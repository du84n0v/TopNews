-- 1-QADAM: Trigger funksiyasini yaratish yoki yangilash
CREATE OR REPLACE FUNCTION process_article_like_change()
    RETURNS TRIGGER AS $$
BEGIN
    -- =========================================================================
    -- 1. YANGI REAKSIYA QO'SHILGANDA (INSERT)
    -- =========================================================================
    IF (TG_OP = 'INSERT') THEN
        IF NEW.status = 'LIKED' THEN
            UPDATE article
            SET like_count = COALESCE(like_count, 0) + 1
            WHERE id = NEW.article_id;
        ELSIF NEW.status = 'DISLIKED' THEN
            UPDATE article
            SET dislike_count = COALESCE(dislike_count, 0) + 1
            WHERE id = NEW.article_id;
        END IF;

        -- =========================================================================
        -- 2. REAKSIYA O'ZGARGANDA (UPDATE - Masalan: Liked dan Disliked ga yoki aksincha)
        -- =========================================================================
    ELSIF (TG_OP = 'UPDATE') THEN
        -- Faqat status rostdan ham o'zgargan bo'lsa ishlaydi
        IF OLD.status != NEW.status THEN
            -- Liked -> Disliked bo'lgan holat
            IF OLD.status = 'LIKED' AND NEW.status = 'DISLIKED' THEN
                UPDATE article
                SET like_count = GREATEST(0, COALESCE(like_count, 1) - 1),
                    dislike_count = COALESCE(dislike_count, 0) + 1
                WHERE id = NEW.article_id;
                -- Disliked -> Liked bo'lgan holat
            ELSIF OLD.status = 'DISLIKED' AND NEW.status = 'LIKED' THEN
                UPDATE article
                SET dislike_count = GREATEST(0, COALESCE(dislike_count, 1) - 1),
                    like_count = COALESCE(like_count, 0) + 1
                WHERE id = NEW.article_id;
            END IF;
        END IF;

        -- =========================================================================
        -- 3. REAKSIYA OLIB TASHLANSA / O'CHIRILSA (DELETE)
        -- =========================================================================
    ELSIF (TG_OP = 'DELETE') THEN
        IF OLD.status = 'LIKED' THEN
            UPDATE article
            SET like_count = GREATEST(0, COALESCE(like_count, 1) - 1)
            WHERE id = OLD.article_id;
        ELSIF OLD.status = 'DISLIKED' THEN
            UPDATE article
            SET dislike_count = GREATEST(0, COALESCE(dislike_count, 1) - 1)
            WHERE id = OLD.article_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- 2-QADAM: Eski trigger bo'lsa o'chirib, yangisini jadvalga bog'lash
DROP TRIGGER IF EXISTS trg_article_like_sync ON article_like;

CREATE TRIGGER trg_article_like_sync
    AFTER INSERT OR UPDATE OR DELETE ON article_like
    FOR EACH ROW
EXECUTE FUNCTION process_article_like_change();
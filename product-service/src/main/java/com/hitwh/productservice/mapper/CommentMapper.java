package mapper;

import entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * 根据商品id获取评论列表
     */
    @Select("SELECT * FROM comment_views WHERE pid = #{productId}")
    List<Comment> getCommentsByProductId(@Param("productId") int productId);

    /**
     * 添加评论
     */
    @Insert("INSERT INTO comment(content, uid, pid) VALUES(#{comment.content}, #{comment.uid}, #{comment.pid})")
    boolean addComment(@Param("comment") Comment comment);
}

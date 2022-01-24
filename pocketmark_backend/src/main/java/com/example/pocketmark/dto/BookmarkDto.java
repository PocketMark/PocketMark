package com.example.pocketmark.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.pocketmark.domain.Bookmark;
import com.example.pocketmark.domain.Folder;
import com.example.pocketmark.domain.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkDto {
    

    public static interface OnlyBookmarkId{
        Long getId();
    }

    


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class BookmarkCreateReq{
        @NotNull
        @JsonProperty("folderId")
        @JsonAlias("folder-id")
        private Long folderId;
        
        
        @NotNull
        @JsonProperty("bookmarkId")
        @JsonAlias("bookmark-id")
        private Long bookmarkId;

        @NotNull @NotBlank
        @Size(max=50)
        private String name;
        
        @NotNull @NotBlank
        private String url;
        
        @Size(max=50)
        private String comment;

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @ToString
        public static class BookmarkCreateServiceReq{
            private Long bookmarkId;
            private Long folderId;
            private String name;
            private String url;
            private String comment;

            public Bookmark toEntity(Folder folder){

                return Bookmark.builder()
                        .folder(folder)
                        .bookmarkId(this.bookmarkId)
                        .folderId(this.folderId)
                        .name(this.name)
                        .url(this.url)
                        .comment(this.comment)
                        .visitCount(0)
                        .build();
            }       
        }

        public BookmarkCreateServiceReq toServiceReq(){
            return BookmarkCreateServiceReq.builder()
                    .bookmarkId(bookmarkId)
                    .folderId(folderId)
                    .name(name)
                    .url(url)
                    .comment(comment)
                    .build();
        }


        public Bookmark toEntity(Folder folder, Long userId){

            return Bookmark.builder()
                    .folder(folder)
                    .userId(userId)
                    .bookmarkId(this.bookmarkId)
                    .folderId(this.folderId)
                    .name(this.name)
                    .url(this.url)
                    .comment(this.comment)
                    .visitCount(0)
                    .build();
        }

    }


    




    public interface BookmarkRes{
        Long getBookmarkId();
        Long getFolderId();
        String getName();
        String getUrl();
        String getComment();
        Integer getVisitCount();
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BookmarkResImpl implements BookmarkRes{
        private Long bookmarkId;
        private Long folderId;
        private String name;
        private String url;
        private String comment;
        private Integer visitCount;

        @QueryProjection
        public BookmarkResImpl(
            String name, String url, String comment,
            Long folderId, Integer visitCount
        ){
            this.name=name;
            this.url=url;
            this.comment=comment;
            this.folderId=folderId;
            this.visitCount=visitCount;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BookmarkUpdateReq{ //UpdateReq 는 Validation이 필요없음, API단에서 BookmarkId Validation만 필요 
        private Long bookmarkId;
        private Long folderId;
        private String name;
        private String url;
        private String comment;
        private Integer visitCount;

        public BookmarkUpdateServiceReq toServiceReq(){
            return BookmarkUpdateServiceReq.builder()
                    .bookmarkId(this.bookmarkId)
                    .folderId(this.folderId)
                    .name(this.name)
                    .url(this.url)
                    .comment(this.comment)
                    .visitCount(this.visitCount)
                    .build();
        }
    }
    

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BookmarkUpdateServiceReq{
        private Long bookmarkId;
        private Long folderId;
        private String name;
        private String url;
        private String comment;
        private Integer visitCount;
        
    }


    public static class BookmarkPositionUpdateReq{
        @NotNull(message = "FolderId Needed to move bookmark.")
        private Long folderId;
    }


}

package org.launchcode.library.models;

public class GoogleBookVolumeInfo {
        private String title;
        private String[] authors;
        private String description;
        private String mainCategory;
        private ImageLinks imageLinks;
        private String[] categories;
        private Double averageRating;




    public GoogleBookVolumeInfo(String title, String[] authors, String description, String mainCategory, ImageLinks imageLinks, Double averageRating, String[] categories) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.mainCategory = mainCategory;
        this.imageLinks = imageLinks;
        this.averageRating = averageRating;
        this.categories = categories;
    }

    public String getTitle() {
            return title;
        }


        public void setTitle(String title) {
            this.title = title;
        }

        public String[] getAuthors() {
            return authors;
        }

        public void setAuthors(String[] authors) {
            this.authors = authors;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
    // Getters and setters

    }

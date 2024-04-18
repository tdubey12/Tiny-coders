package org.launchcode.library.models;

public class GoogleBookItem {
        private GoogleBookVolumeInfo volumeInfo;

        public GoogleBookVolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(GoogleBookVolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }
    }

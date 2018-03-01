package com.example.dimitri.helpdeal.azureClasses.azureModels;
public class ObservedPerson {


        @com.google.gson.annotations.SerializedName("id")
        private String ob_id;
        public void setId(String uid) {
            this.ob_id = uid;
        }
        public String getId() {
            return ob_id;
        }

        @com.google.gson.annotations.SerializedName("ob_current_user_id")
        private String ob_current_user_id;
        public String getCurrentUserID() {
            return ob_current_user_id;
        }
        public void setCurrentUserID (String currentUserId) { this.ob_current_user_id = currentUserId;}

        @com.google.gson.annotations.SerializedName("ob_observed_user_id")
        private String ob_observed_user_id;
        public String getObservedUserID() {
            return ob_observed_user_id;
        }
        public void setObservedUserID(String observedUserID) { this.ob_observed_user_id = observedUserID; }

    public ObservedPerson (){};

        @Override
        public boolean equals(Object o) {
            return o instanceof com.example.dimitri.helpdeal.azureClasses.azureModels.ObservedPerson && ((ObservedPerson) o).ob_id == ob_id;
        }
    }

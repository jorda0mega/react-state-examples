(ns react-state-examples.events
  (:require [react-state-examples.db :as db]))

;(defn login
;  []
;  (swap! db/state assoc :auth? true))
;
;(defn logout
;  []
;  (swap! db/state assoc :auth? false))
;
;(defn toggle-user-dropdown
;  []
;  (let [dropdown (:user-dropdown? @db/state)]
;    (swap! db/state assoc :user-dropdown? (not dropdown))))
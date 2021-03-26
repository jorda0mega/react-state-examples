(ns react-state-examples.app
  (:require [reagent.dom :as dom]
            [react-state-examples.views :as views]
            [react-state-examples.db :as db]
            [re-frame.core :as rf]))

(rf/reg-event-db
  :initialize
  (fn [_ _]
    db/initial-state))

(defn app
  []
  [views/Tabs])

(defn render
  []
  (dom/render [app]
              (.getElementById js/document "app")))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load clear-cache-and-render! []
  (rf/clear-subscription-cache!)
  (render))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (rf/dispatch-sync [:initialize])
  (render))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))

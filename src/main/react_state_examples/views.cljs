(ns react-state-examples.views
  (:require [react-state-examples.events :as events]
            [react-state-examples.db :as db]
            [re-frame.core :as rf]))

(rf/reg-event-db
  :select-tab
  (fn [db [_ new-selected-tab]]
    (.log js/console (get-in db [:tabs]))
    (update-in db [:tabs] #(map (fn [tab]
                                  (assoc tab :selected (= (:id tab) new-selected-tab))) %))))

(defn change-selected-tab
  "changes the selected attribute of a tab to true"
  [tab tab-id]
  (assoc tab :selected (= (:id tab) tab-id)))

(defn dispatch-select-tab-event
  [new-selected-tab]
  (.log js/console new-selected-tab)
  (rf/dispatch [:select-tab new-selected-tab]))

(rf/reg-sub
  :tabs
  (fn [db _]
    (:tabs db)))

(rf/reg-sub
  :selected-tab
  (fn [db _]
    (.log js/console "getting called")
    (->> db
        :tabs
        (filter #(:selected %))
        first
        :id)))
    ;(.log js/console (str "selected this tab: " selected-tab))))

(defn Tab
  "component to create a styled tab based on selection"
  [idx tab]
  (let [id (:id tab)
        label (:label tab)
        selected? (:selected tab)]
    (if selected?
      [:a.bg-gray-100.text-gray-700.px-3.py-2.font-large.text-md.rounded-md
       {:key      idx
        :href     "#" :aria-current "page"
        :on-click #(dispatch-select-tab-event id)}
       label]
      [:a.text-gray-500.hover:text-gray-700.px-3.py-2.font-large.text-md.rounded-md
       {:key      idx
        :href     "#"
        :on-click #(dispatch-select-tab-event id)}
       label])))

(defn Tabs
  "Component to display tabs"
  []
  [:div.m-8
   [:div.hidden.sm:block
    [:nav.flex-grow.space-x-4 {:aria-label "Tabs"}
     (let [tabs @(rf/subscribe [:tabs])]
       (map-indexed Tab tabs))]]])
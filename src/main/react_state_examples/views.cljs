(ns react-state-examples.views
  (:require [react-state-examples.events :as events]
            [react-state-examples.db :as db]
            [re-frame.core :as rf]))

(rf/reg-event-db
  :select-tab
  (fn [db [_ new-selected-tab]]))
;(let [tabs (:tabs db)]
;  (map (fn [idx] (update-in idx [:selected (=)]))))
;(assoc db :selected-tab new-selected-tab)))

(defn change-selected-tab
  "changes the selected attribute of a tab to true"
  [db tab-id]
  (update-in db [:tabs tab-id :selected] (identity true)))

(defn dispatch-select-tab-event
  [new-selected-tab]
  (.log js/console new-selected-tab)
  (rf/dispatch [:select-tab new-selected-tab]))

(rf/reg-sub
  :tabs
  (fn [db _]
    (:tabs db)))

(defn Tab
  "component to create a styled tab based on selection"
  [idx tab]
  (let [id (:id tab)
        label (:label tab)
        selected? (:selected tab)]
    (if selected?
      [:a.bg-gray-100.text-gray-700.px-3.py-2.font-medium.text-sm.rounded-md {:key      idx
                                                                              :href     "#" :aria-current "page"
                                                                              :on-click #(dispatch-select-tab-event id)}
       label]
      [:a.text-gray-500.hover:text-gray-700.px-3.py-2.font-medium.text-sm.rounded-md {:key      idx
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
;[:a.text-gray-500.hover:text-gray-700.px-3.py-2.font-medium.text-sm.rounded-md {:href "#"} "Hello React"]
;[:a.text-gray-500.hover:text-gray-700.px-3.py-2.font-medium.text-sm.rounded-md {:href "#"} "Lorem Ipsum"]
;[:a.bg-gray-100.text-gray-700.px-3.py-2.font-medium.text-sm.rounded-md {:href "#" :aria-current "page"} "Login Form"]
;[:a.text-gray-500.hover:text-gray-700.px-3.py-2.font-medium.text-sm.rounded-md {:href "#"} "Fun People"]]]])
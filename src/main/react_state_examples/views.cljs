(ns react-state-examples.views
  (:require
    [re-frame.core :as rf]
    [taoensso.timbre :as timbre]))

(rf/reg-event-db
  :select-tab
  (fn [db [_ new-selected-tab]]
    (timbre/info (get-in db [:tabs]))
    (assoc db :active-tab-id new-selected-tab)))

(defn change-selected-tab
  "changes the selected attribute of a tab to true"
  [tab tab-id]
  (assoc tab :selected (= (:id tab) tab-id)))

(rf/reg-sub
  :tabs
  (fn [db _]
    (let [tabs (:tabs db)]
      (map
        (fn [[id tab]]
          (assoc tab :id id))
        tabs))))

(rf/reg-sub
  :ordered-tabs
  :<- [:tabs]
  (fn [tabs _]
    (->> tabs
         (sort-by :order))))

(rf/reg-sub
  :active-tab-id
  (fn [db _]
    (timbre/info "getting called")
    (:active-tab-id db)))

(defn Tab
  "component to create a styled tab based on selection"
  [idx tab]
  (let [active-tab-id @(rf/subscribe [:active-tab-id])
        tab-id (:id tab)
        label (:label tab)
        selected? (= tab-id active-tab-id)]
    (timbre/info tab-id)
    (if selected?
      [:a.bg-gray-100.text-gray-700.px-3.py-2.font-large.text-md.rounded-md
       {:key      idx
        :href     "#" :aria-current "page"
        :on-click #(rf/dispatch [:select-tab tab-id])}
       label]
      [:a.text-gray-500.hover:text-gray-700.px-3.py-2.font-large.text-md.rounded-md
       {:key      idx
        :href     "#"
        :on-click #(rf/dispatch [:select-tab tab-id])}
       label])))

(defn Tabs
  "Component to display tabs"
  []
  [:div.m-8
   [:div.hidden.sm:block
    [:nav.flex-grow.space-x-4 {:aria-label "Tabs"}
     (let [tabs @(rf/subscribe [:ordered-tabs])]
       (timbre/info tabs)
       (doall (map-indexed Tab tabs)))]]])
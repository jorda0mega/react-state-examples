(ns react-state-examples.components.lorem-ipsum
  (:require
    [re-frame.core :as rf]
    [taoensso.timbre :as timbre]))

(def default-paragraph
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus faucibus at magna sit amet tristique. Suspendisse ut varius dui, tincidunt semper sapien. Nullam bibendum eros lectus, eu posuere velit ultrices sed. Proin consectetur lacus nec metus ullamcorper, ac tempus felis eleifend. Donec eu euismod nisl. Morbi fringilla justo sit amet blandit dictum. Fusce sollicitudin ipsum ut mauris posuere pharetra. Praesent vitae elit nec ex placerat faucibus a in diam. Sed bibendum sit amet dui at facilisis. Vivamus vitae felis lacinia, gravida lectus id, placerat ipsum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Integer feugiat, sem in interdum tempor, nisi enim mollis nibh, luctus laoreet massa ligula nec ex.")

;;--------------------------------------------------------------------------------
;; EVENTS
;;--------------------------------------------------------------------------------
(rf/reg-event-db
  ::add-paragraph
  (fn [db _]
    (update db ::paragraphs conj default-paragraph)))

(rf/reg-event-db
  ::remove-paragraph
  (fn [db _]
    (if-not (empty? (::paragraphs db))
      (update db ::paragraphs pop)
      db)))

;;--------------------------------------------------------------------------------
;; SUBSCRIPTIONS
;;--------------------------------------------------------------------------------

(rf/reg-sub
  ::paragraphs
  (fn [db _]
    (::paragraphs db)))

(rf/reg-sub
  ::num-paragraphs
  :<- [::paragraphs]
  (fn [paragraphs _]
    (count paragraphs)))

;;--------------------------------------------------------------------------------
;; VIEWS 
;;--------------------------------------------------------------------------------

(defn Paragraph
  "section displaying paragraphs"
  [idx]
  [:p.text-gray-500 {:key idx} default-paragraph])

(defn LoremIpsum
  "component to display the lorem ipsum tab content"
  []
  (let [paragraphs @(rf/subscribe [::paragraphs])
        num-paragraphs @(rf/subscribe [::num-paragraphs])]
    [:div.m-8.space-y-6
     [:p.text-3xl (str "Number of Lorem Ipsum paragraphs: " num-paragraphs)]
     [:div.space-x-4
      [:button.inline-flex.items-center.px-6.py-3.border.border-transparent.text-base.font-medium.rounded-md.shadow-sm.text-white.bg-blue-600.hover:bg-blue-700.focus:outline-none.focus:ring-2.focus:ring-offset-2.focus:ring-blue-500
       {:type     "button"
        :on-click #(rf/dispatch [::add-paragraph])}
       "Add Paragraph"]
      [:button.inline-flex.items-center.px-6.py-3.border.border-transparent.text-base.font-medium.rounded-md.shadow-sm.text-white.bg-blue-600.hover:bg-blue-700.focus:outline-none.focus:ring-2.focus:ring-offset-2.focus:ring-blue-500
       {:type     "button"
        :on-click #(rf/dispatch [::remove-paragraph])}
       "Remove Paragraph"]]
     [:div.space-y-6
      (map-indexed Paragraph paragraphs)]]))

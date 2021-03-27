(ns react-state-examples.components.lorem-ipsum
  (:require [re-frame.core :as rf]))

(def default-paragraph
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus faucibus at magna sit amet tristique. Suspendisse ut varius dui, tincidunt semper sapien. Nullam bibendum eros lectus, eu posuere velit ultrices sed. Proin consectetur lacus nec metus ullamcorper, ac tempus felis eleifend. Donec eu euismod nisl. Morbi fringilla justo sit amet blandit dictum. Fusce sollicitudin ipsum ut mauris posuere pharetra. Praesent vitae elit nec ex placerat faucibus a in diam. Sed bibendum sit amet dui at facilisis. Vivamus vitae felis lacinia, gravida lectus id, placerat ipsum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Integer feugiat, sem in interdum tempor, nisi enim mollis nibh, luctus laoreet massa ligula nec ex.")

(defn Paragraph
  "section displaying paragraphs"
  [idx]
  [:p.text-gray-500 {:key idx} default-paragraph])

(rf/reg-sub
  :paragraphs
  (fn [db _]
    (:paragraphs db)))

(rf/reg-sub
  :paragraphs-count
  (fn [db _]
    (count (:paragraphs db))))

(rf/reg-event-db
  :add-paragraph
  (fn [db [_ _]]
    (update-in db [:paragraphs] conj default-paragraph)))

(rf/reg-event-db
  :remove-paragraph
  (fn [db [_ _]]
    (when (> @(rf/subscribe [:paragraphs-count]) 0)
      (update-in db [:paragraphs] pop default-paragraph))))

(defn LoremIpsum
  "component to display the lorem ipsum tab content"
  []
  [:div.m-8.space-y-6
   [:p.text-3xl (str "Number of Lorem Ipsum paragraphs: " @(rf/subscribe [:paragraphs-count]))]
   [:div.space-x-4
    [:button.inline-flex.items-center.px-6.py-3.border.border-transparent.text-base.font-medium.rounded-md.shadow-sm.text-white.bg-blue-600.hover:bg-blue-700.focus:outline-none.focus:ring-2.focus:ring-offset-2.focus:ring-blue-500
     {:type "button"
      :on-click #(rf/dispatch [:add-paragraph])}
     "Add Paragraph"]
    [:button.inline-flex.items-center.px-6.py-3.border.border-transparent.text-base.font-medium.rounded-md.shadow-sm.text-white.bg-blue-600.hover:bg-blue-700.focus:outline-none.focus:ring-2.focus:ring-offset-2.focus:ring-blue-500
     {:type "button"
      :on-click #(rf/dispatch [:remove-paragraph])}
     "Remove Paragraph"]]
   [:div.space-y-6
    (map-indexed Paragraph @(rf/subscribe [:paragraphs]))]])

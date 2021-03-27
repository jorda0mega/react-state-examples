(ns react-state-examples.components.lorem-ipsum)

(defn LoremIpsum
  "component to display the lorem ipsum tab content"
  []
  [:div.m-8.space-y-6
   [:p.text-3xl "Number of Lorem Ipsum paragraphs: "]
   [:div.space-x-4
    [:button.inline-flex.items-center.px-6.py-3.border.border-transparent.text-base.font-medium.rounded-md.shadow-sm.text-white.bg-blue-600.hover:bg-blue-700.focus:outline-none.focus:ring-2.focus:ring-offset-2.focus:ring-blue-500
     {:type "button"}
     "Add Paragraph"]
    [:button.inline-flex.items-center.px-6.py-3.border.border-transparent.text-base.font-medium.rounded-md.shadow-sm.text-white.bg-blue-600.hover:bg-blue-700.focus:outline-none.focus:ring-2.focus:ring-offset-2.focus:ring-blue-500
     {:type "button"}
     "Remove Paragraph"]]])

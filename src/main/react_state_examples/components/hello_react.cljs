(ns react-state-examples.components.hello-react)

(defn HelloReact
  "component to display the hello react tab content"
  []
  [:div.m-8.space-y-6
   [:p.text-3xl "Hello React!"]
   [:p.text-gray-500 "React makes it painless to create interactive UIs. Design simple views for each state in your application, and React will efficiently update and render just the right components when your data changes."]
   [:p.text-gray-500 "Declarative views make your code more predictable and easier to debug."]])

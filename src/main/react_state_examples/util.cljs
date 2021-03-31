(ns react-state-examples.util
  (:require
    [goog.dom :as gdom]
    [goog.dom.forms :as gforms]))

;; TODO: this could be variadic with some options
(defn form->values
  "Returns the values from a DOM Form Element as a ClojureScript Map"
  [el]
  (let [el (gdom/getElement el)
        form-data-map (gforms/getFormDataMap el)
        form-data-obj (.toObject ^goog.structs.Map form-data-map)
        form-data (js->clj form-data-obj)]
    (reduce
      (fn [acc [k itm]]
        (assoc acc (keyword k) (first itm)))
      {}
      form-data)))

(ns react-state-examples.db
  (:require [reagent.core :as r]
            [react-state-examples.components.lorem-ipsum :refer [default-paragraph]]))

(defonce initial-state {:tabs [{:id :hello-react
                                :label "Hello React"
                                :selected true}
                               {:id :lorem-ipsum
                                :label "Lorem Ipsum"
                                :selected false}
                               {:id :login-form
                                :label "Login Form"
                                :selected false}
                               {:id :fun-people
                                :label "Fun People"
                                :selected false}]
                        :paragraphs [default-paragraph default-paragraph]})

;(defonce initial-state {:tabs {:hello-react
;                               {:label    "Hello React"
;                                :selected true}
;                               :lorem-ipsum
;                               {:label    "Lorem Ipsum"
;                                :selected false}
;                               :login-form
;                               {:label    "Login Form"
;                                :selected false}
;                               :fun-people
;                               {:label    "Fun People"
;                                :selected false}}})
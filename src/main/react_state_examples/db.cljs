(ns react-state-examples.db
  (:require [reagent.core :as r]
            [react-state-examples.components.lorem-ipsum :refer [default-paragraph]]))

;(defonce initial-state {:tabs [{:id :hello-react
;                                :label "Hello React"
;                                :selected true}
;                               {:id :lorem-ipsum
;                                :label "Lorem Ipsum"
;                                :selected false}
;                               {:id :login-form
;                                :label "Login Form"
;                                :selected false}
;                               {:id :fun-people
;                                :label "Fun People"
;                                :selected false}]
;                        :paragraphs [default-paragraph default-paragraph]})

(defonce initial-state {:tabs                                                          {"TAB_HELLO_REACT"
                                                                                        {:label "Hello React"
                                                                                         :order 1}
                                                                                        "TAB_LOREM_IPSUM"
                                                                                        {:label "Lorem Ipsum"
                                                                                         :order 2}
                                                                                        "TAB_LOGIN_FORM"
                                                                                        {:label "Login Form"
                                                                                         :order 3}
                                                                                        "TAB_FUN_PEOPLE"
                                                                                        {:label "Fun People"
                                                                                         :order 4}}
                        :active-tab-id                                                 "TAB_HELLO_REACT"
                        :react-state-examples.components.login-form/missing-username? false
                        :react-state-examples.components.login-form/missing-password? false
                        :react-state-examples.components.lorem-ipsum/paragraphs
                                                                                       [default-paragraph default-paragraph]})

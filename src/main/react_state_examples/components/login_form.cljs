(ns react-state-examples.components.login-form
  (:require
    [re-frame.core :as rf]
    [taoensso.timbre :as timbre]
    [react-state-examples.util :refer [form->values]]))

;;--------------------------------------------------------------------------------
;; EVENTS
;;--------------------------------------------------------------------------------
(rf/reg-event-db
  ::validate-form
  (fn [db [_ form-values]]
    (let [username (:username form-values)
          password (:password form-values)]
      (timbre/info "empty username: " (empty? username))
      (timbre/info "empty password: " (empty? password))
      (assoc-in db [:login-form] {:missing-username (empty? username)
                                  :missing-password (empty? password)}))))

;;--------------------------------------------------------------------------------
;; SUBSCRIPTIONS
;;--------------------------------------------------------------------------------

(rf/reg-sub
  ::error-count
  (fn [db _]
    (count (filter #(= true (val %)) (:login-form db)))))

(rf/reg-sub
  ::show-error
  :<- [::error-count]
  (fn [error-count]
    (> error-count 0)))

(rf/reg-sub
  ::need-username?
  (fn [db _]
    (get-in db [:login-form :missing-username])))
;
(rf/reg-sub
  ::need-password?
  (fn [db _]
    (get-in db [:login-form :missing-password])))

;;--------------------------------------------------------------------------------
;; VIEWS
;;--------------------------------------------------------------------------------
(defn FormErrorMessage
  []
  (let [error-count @(rf/subscribe [::error-count])
        need-username? @(rf/subscribe [::need-username?])
        need-password? @(rf/subscribe [::need-password?])]
    [:div.rounded-md.bg-red-50.p-4
     [:div.flex
      [:div.flex-shrink-0
       [:svg.h-5.w-5.text-red-400 {:xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 20 20" :fill "currentColor" :aria-hidden "true"}
        [:path {:fill-rule "evenodd" :d "M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" :clip-rule "evenodd"}]]]
      [:div.ml-3
       [:h3.text-sm.font-medium.text-red-800 (str "There " (if (= error-count 1) "was " "were ") error-count " error" (if (= error-count 1) "" "s") " with your submission")]
       [:div.mt-2.text-sm.text-red-700
        [:ul.list-disc.pl-5.space-y-1
         (when need-username?
           [:li "Please enter a username"])
         (when need-password?
           [:li "Please enter a password"])]]]]]))

(defn LoginForm
  "component to display the login form tab content"
  []
  (let [display-error-msg? @(rf/subscribe [::show-error])]
    [:div.flex.items-center.justify-center.py-6.px-4.sm:px-6.lg:px-8
     [:div.max-w-md.w-full.space-y-8
      [:div
       [:h2.mt-6.text-center.text-3xl.text-gray-900 "Login"]]
      (when display-error-msg?
        [FormErrorMessage])
      [:form.mt-8.space-y-6
       {:action    "#" :method "POST" :type "submit"
        :on-submit (fn [js-evt]
                     (let [js-target (.-target js-evt)
                           form-values (form->values js-target)]
                       (.preventDefault js-evt)
                       (rf/dispatch [::validate-form form-values])))}
       [:input {:type "hidden" :name "remember" :value "true"}]

       [:div.rounded-md.shadow-sm.-space-y-px
        [:div
         [:label.sr-only {:for "username"} "Username"]
         [:input#username.appearance-none.rounded-none.relative.block.w-full.px-3.py-2.border.border-gray-300.placeholder-gray-500.text-gray-900.rounded-t-md.focus:outline-none.focus:ring-blue-500.focus:border-blue-500.focus:z-10.sm:text-sm
          {:name "username" :type "username" :autocomplete "username" :placeholder "Username"}]]
        [:div
         [:label.sr-only {:for "password"} "Password"]
         [:input#password.appearance-none.rounded-none.relative.block.w-full.px-3.py-2.border.border-gray-300.placeholder-gray-500.text-gray-900.rounded-b-md.focus:outline-none.focus:ring-blue-500.focus:border-blue-500.focus:z-10.sm:text-sm
          {:name "password" :type "password" :autocomplete "current-password" :placeholder "Password"}]]]
       [:div
        [:button.group.relative.w-full.flex.justify-center.py-2.px-4.border.border-transparent.text-sm.font-medium.rounded-md.text-white.bg-blue-600.hover:bg-blue-700.focus:outline-none.focus:ring-2.focus:ring-offset-2.focus:ring-blue-500
         {:type "submit"}
         [:span.absolute.left-0.inset-y-0.flex.items-center.pl-3
          [:svg.h-5.w-5.text-blue-500.group-hover:text-blue-400
           {:xmlns       "http://www.w3.org/2000/svg"
            :viewBox     "0 0 20 20"
            :fill        "currentColor"
            :aria-hidden "true"}
           [:path
            {:fill-rule "evenodd"
             :d         "M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z"
             :clip-rule "evenodd"}]]]
         "Sign in"]]]]]))

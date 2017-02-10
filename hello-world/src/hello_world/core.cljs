(ns hello-world.core
  (:require [crate.core :as c]
            [formative.core :as f]
            [formative.dom :as fd])
  (:require-macros [dommy.macros :refer [node sel sel1]]))

(enable-console-print!)

(println "This text is printed from src/hello-world/core.cljs. Go ahead and edit it and see reloading in action.")

(defn trivial-form []
  {:fields [{:name :foo}
            {:name :bar :type :select
             :options [["sss" "sss"]
                       ["fff" "fff"]
                       ["ははあは" "べべべべ"]]}]
   :validations [[:required [:foo]]
                 [:max-length 3 [:foo]]]
   :renderer :bootstrap3-stacked})

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn show-problem []
  (fd/show-problems (trivial-form) (.getElementById js/document "form")
                    [{:keys ["やう゛ぁい"],
                      :msg "マネージャーと部門マネージャーは兼務出来ません"}]))

(defn clear-problem []
  (fd/clear-problems (.getElementById js/document "form")))

(defn serialize []
  (fd/serialize (fd/get-form-el (.getElementById js/document "form"))))

(defn form-el []
  (fd/get-form-el (.getElementById js/document "form")))

(defn remove-form []
  (some-> (.getElementById js/document "form")
          .-firstChild
          .remove))

(defn create-form []
  (-> (.getElementById js/document "form")
      (.appendChild (c/html (f/render-form (trivial-form))))))

(defn do-handle-submit []
  (fd/handle-submit
   (trivial-form)
   (.getElementById js/document "form")
   #(js/alert "submit event!!!")))

(defn reset-form []
  (let [form-elem (.getElementById js/document "form")]
    (remove-form)
    (create-form)
    (do-handle-submit)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state (constantly))
  (reset-form))

(reset-form)

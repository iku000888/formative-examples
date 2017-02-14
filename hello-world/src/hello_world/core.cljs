(ns hello-world.core
  (:require [crate.core :as c]
            [formative.core :as f]
            [formative.dom :as fd])
  (:import [goog.i18n DateTimeFormat DateTimeParse]
           goog.ui.InputDatePicker))

(enable-console-print!)

(println "This text is printed from src/hello-world/core.cljs. Go ahead and edit it and see reloading in action.")

(defn trivial-form []
  {:fields [{:name :foo}
            {:name :bar :type :select
             :options [["sss" "sss"]
                       ["fff" "fff"]
                       ["ははあは" "べべべべ"]]}
            {:name :date :type :date}]
   :validations [[:required [:foo]]
                 [:max-length 3 [:foo]]
                 [:after #inst"2020" :date]]
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
  (while (.-firstChild (.getElementById js/document "form"))
    (.removeChild (.getElementById js/document "form")
                  (.-firstChild (.getElementById js/document "form")))))


#_(some->> (.getElementById js/document "form")
           .-childNodes
           array-seq
           (map #(.remove %))
           )

(defn create-form []
  (-> (.getElementById js/document "form")
      (.appendChild (c/html (f/render-form (trivial-form))))))

(defn do-handle-submit []
  (fd/handle-submit
   (trivial-form)
   (.getElementById js/document "form")
   #(js/alert "submit event!!!")))

(defn render-date-picker []
  (let [pat "yyyy-MM-dd"
        fmt (DateTimeFormat. pat)
        psr (DateTimeParse. pat)
        idp (InputDatePicker. fmt psr)]
    (.decorate idp (.getElementById js/document "field-date"))))

(defn reset-form []
  (remove-form)
  (create-form)
  (render-date-picker)
  (do-handle-submit))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state (constantly))
  (reset-form))

(reset-form)

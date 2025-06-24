package com.bg.bancoguayaquilcertificates.services

import android.util.Log
import io.heap.core.BuildConfig
import io.heap.core.Heap

class AnalyticsService private constructor() {

    fun sendEvent(event: AnalyticsEvent, parameters: Map<String, Any>) {
        Heap.track(event.value, parameters)

        val identity = Heap.getIdentity()
        val userId = Heap.getUserId()

        //if (BuildConfig.DEBUG) {
        Log.d(TAG, "AnalyticsEvent: ${event.value} with parameters: $parameters - Identity: $identity - UserId: $userId")
        //}
    }

    companion object {
        @JvmStatic
        val instance: AnalyticsService = AnalyticsService()
        private const val TAG = "AnalyticsService"
    }
}

enum class AnalyticsEvent(val value: String) {
    // Screen Events
    SCREEN_VIEW("BM_Screen_View"),
    SCREEN_BOTTOM("BM_Screen_Bottom"),

    // Journey Events
    JOURNEY_VIEW("BM_Journey_View"),

    // Contextual Events
    CONTEXTUAL_VIEW("BM_Contextual_View"),

    // ActionSheet Events
    ACTION_SHEET_VIEW("BM_ActionSheet_View"),

    // Toast Events
    TOAST_VIEW("BM_Toast_View"),

    // Alert Events
    ALERT_VIEW("BM_Alert_View"),
    ALERT_BUTTON_TAP("BM_Alert_Button_Tap"),

    // Link Events
    LINK_TAP("BM_Link_Tap"),

    // DeepLink Events
    DEEP_LINK("BM_deeplink"),

    // Button Events
    BUTTON_TAP("BM_Button_Tap"),

    // Chip Events
    CHIP_TAP("BM_Chip_Tap"),

    // SwipeableButton Events
    SWIPEABLE_BUTTON_SWIPE("BM_SwipeableButton_Swipe"),

    // FabButton Events
    FAB_BUTTON_TAP("BM_FabButton_Tap"),

    // Icon Events
    ICON_TAP("BM_Icon_Tap"),

    // SearchBar Events
    SEARCH_BAR_TAP("BM_SearchBar_Tap"),

    // TapBar Events
    TAP_BAR_TAP("BM_TapBar_Tap"),

    // Input Events
    INPUT_TAP("BM_Input_Tap"),
    INPUT_COMMIT("BM_Input_Commit"),

    // Segment Events
    SEGMENT_TAP("BM_Segment_Tap"),

    // Text Events
    TEXT_TAP("BM_Text_Tap"),
    TEXT_VIEW("BM_Text_View"),

    // Item Events
    ITEM_TAP("BM_Item_Tap"),

    // Animation Events
    ANIMATION_TAP("BM_Animation_Tap"),

    // Checkbox Events
    CHECKBOX_TAP("BM_Checkbox_Tap"),

    // Toggle Events
    TOGGLE_TAP("BM_Toggle_Tap"),

    // Video Events
    VIDEO_TAP("BM_Video_Tap"),

    // Servicio Events
    SERVICIO_EXITOSO("BM_Servicio_Exitoso"),
    SERVICIO_ERROR("BM_Servicio_Error"),

    // Banner Events
    BANNER_TAP("BM_Banner_Tap"),

    // Slide Events
    SLIDE_SWIPE("BM_Slide_Swipe"),
    SLIDE_TAP("BM_Slide_Tap"),

    // Chart Events
    CHART_POINT_TAP("BM_Chart_Point_Tap"),

    // RadioButton Events
    RADIO_BUTTON_TAP("BM_RadioButton_Tap"),

    // Card Events
    CARD_TAP("BM_Card_Tap"),

    // Avatar Events
    AVATAR_TAP("BM_Avatar_Tap"),

    // Permission Events
    PERMISSION_VIEW("BM_Permission_View"),
    PERMISSION_CHECK("BM_Permission_Check"),
    PERMISSION_BUTTON_TAP("BM_Permission_Button_Tap"),

    // Accordion Events
    ACCORDION_TAP("BM_Accordion_Tap"),

    // Sortable Events
    SORTABLE_MOVE("BM_Sortable_Move"),
    SORTABLE_ORDER("BM_Sortable_Order"),

    // Datetime Events
    DATETIME_VIEW("BM_Datetime_View"),
    DATETIME_BUTTON_TAP("BM_Datetime_Button_Tap"),

    // Guide Events
    GUIDE_VIEW("BM_Guide_View"),
    GUIDE_BUTTON_TAP("BM_Guide_Button_Tap")
}

object AnalyticsCategory {
    const val certificados = "Certificados"
}

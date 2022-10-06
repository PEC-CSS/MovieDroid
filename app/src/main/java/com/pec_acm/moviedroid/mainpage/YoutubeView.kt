package com.pec_acm.moviedroid.mainpage

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

class YoutubeView(context: Context,attributeSet: AttributeSet?) : WebView(context,attributeSet) {

    constructor(context: Context) : this(context,null)
    init {
        settings.javaScriptEnabled=true
        settings.layoutAlgorithm= WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.pluginState= WebSettings.PluginState.ON
    }

    override fun loadData(ytId: String, mimeType: String?, encoding: String?) {
        super.loadData("<html><body style=\" margin: 0px; padding: 0px;\"><iframe class=\"youtube-player\" type=\"text/html\" src=\"https://www.youtube.com/embed/$ytId/?&theme=dark&autohide=2&modestbranding=1&showinfo=0\" frameborder=\"0\" style=\"width : 100%; height:100%; margin:0px; padding:0px; border:0;\" allowfullscreen></iframe></body></html>", mimeType, encoding)
    }
}
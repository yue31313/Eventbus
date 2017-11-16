package han_meng_fei_sha.eventbusdemo

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener({ EventBus.getDefault().post(MessageEvent("主线程发送过来的消息事件")) })
        button2.setOnClickListener({ EventBus.getDefault().postSticky(NianEvent("粘性消息事件")) })
        button3.setOnClickListener({
            if (!EventBus.getDefault().isRegistered(this@MainActivity)) {
                EventBus.getDefault().register(this@MainActivity)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this@MainActivity)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun jieShouXiaoXi(messageEvent: MessageEvent) {
        textView.text = messageEvent.name
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun jieShouNianXiaoXi(event: NianEvent) {
        textView.text = event.name
    }
}

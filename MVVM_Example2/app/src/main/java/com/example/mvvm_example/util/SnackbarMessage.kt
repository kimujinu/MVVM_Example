import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.mvvm_example.util.SingleLiveEvent

/**
 * A SingleLiveEvent used for Snackbar messages. Like a [SingleLiveEvent] but also prevents
 * null messages and uses a custom observer.
 *
 *
 * Note that only one observer is going to be notified of changes.
 */
/*
    SingleLiveEvent를 상속하여 R.string의 id를 이용해서 snackbarmessage 이벤트를 발생하는 객체
 */
class SnackbarMessage : SingleLiveEvent<Int>() {

    fun observe(owner: LifecycleOwner, observer: (Int) -> Unit) {
        super.observe(owner, Observer {
            it?.run{
                observer(it)
            }
        })
    }

}

class SnackbarMessageString:SingleLiveEvent<String>(){
    fun observe(owner: LifecycleOwner, observer: (String) -> Unit) {
        super.observe(owner, Observer {
            it?.run{
                observer(it)
            }
        })
    }
}
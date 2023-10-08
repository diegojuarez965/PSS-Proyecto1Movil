package mvc.view.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.ospifakmobileversion.R
import mvc.model.AppModel
import mvc.model.AppModelInjector
import mvc.view.AppViewInjector
import observers.Observable
import observers.Subject

interface LoginView {
    val uiEventObservable: Observable<LoginUiEvent>
    val uiState: LoginUiState

    fun navigateToInitialWindow(user: String)
    fun navigateToRecoverPassword()
}

internal class LoginViewActivity: AppCompatActivity(), LoginView {
    private val onActionSubject = Subject<LoginUiEvent>()

    private lateinit var appModel: AppModel
    private lateinit var title: TextView
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var recoverPassword: Button
    private lateinit var singIn: Button
    private lateinit var loading: ProgressBar

    override val uiEventObservable: Observable<LoginUiEvent> = onActionSubject
    override var uiState: LoginUiState = LoginUiState()

    override fun navigateToInitialWindow(user: String) {
        print(user)
    }

    override fun navigateToRecoverPassword() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initModule()
        initProperties()
        initListeners()
        initObservers()
    }

    private fun initModule() {
        AppViewInjector.init(this)
        appModel = AppModelInjector.getLoginModel()
    }

    private fun initProperties() {
        title = findViewById(R.id.title)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        recoverPassword = findViewById(R.id.recover_password)
        singIn = findViewById(R.id.login)
        loading = findViewById(R.id.loading)
    }

    private fun initListeners() {
        recoverPassword.setOnClickListener {
            hideKeyboard(username)
            hideKeyboard(password)

            notifyRecoverAction()
        }

        singIn.setOnClickListener {
            switchLoadingVisibility()
            updateUser()
            hideKeyboard(username)
            updatePassword()
            hideKeyboard(password)
            switchSingInButton()

            notifySingInAction()
        }
    }

    private fun initObservers() {
        appModel.loginObservable.subscribe {
            value -> loginResult(value)
        }

    }

    private fun updateUser() {
        uiState = uiState.copy(user = username.text.toString())
    }

    private fun updatePassword() {
        uiState = uiState.copy(password = password.text.toString())
    }

    private fun loginResult(result: Boolean) {
        switchLoadingVisibility()
        updateResult(result)
        showMessage()
        if(!result) {
            switchSingInButton()
        }
    }

    private fun updateResult(result: Boolean) {
        if(result) {
            updateSuccesSingIn()
        }
        else {
            updateFailSingIn()
        }
    }

    private fun updateSuccesSingIn() {
        uiState = uiState.copy(
            validUser = true,
            resultInfo = "Ingreso exitoso"
        )
    }

    private fun updateFailSingIn() {
        uiState = uiState.copy(
            validUser = false,
            resultInfo = "Fallo al iniciar sesión"
        )
    }

    private fun switchLoadingVisibility() {
        runOnUiThread {
            if(loading.isVisible) {
                loading.visibility = View.INVISIBLE
            }
            else {
                loading.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage() {
        runOnUiThread {
            Toast.makeText(applicationContext, uiState.resultInfo, Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard(view: View) {
        (this@LoginViewActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun switchSingInButton() {
        runOnUiThread {
            singIn.isEnabled = !singIn.isEnabled
        }
    }

    private fun notifyRecoverAction() {
        onActionSubject.notify(LoginUiEvent.recoverPassword)
    }

    private fun notifySingInAction() {
        onActionSubject.notify(LoginUiEvent.SingIn)
    }










}
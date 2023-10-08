package login.view

import android.content.Intent
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
import login.model.LoginModel
import login.model.LoginModelInjector
import login.model.entities.User
import login.model.entities.User.ClientUser
import login.model.entities.User.EmptyUser
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

    private lateinit var loginModel: LoginModel
    private lateinit var user: User
    private lateinit var title: TextView
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var recoverPassword: Button
    private lateinit var singIn: Button
    private lateinit var loading: ProgressBar

    override val uiEventObservable: Observable<LoginUiEvent> = onActionSubject
    override var uiState: LoginUiState = LoginUiState()

    override fun navigateToInitialWindow(user: String) {
        TODO("Not yet implemented")
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
        LoginViewInjector.init(this)
        loginModel = LoginModelInjector.getLoginModel()
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

            notifySingInAction()
        }
    }

    private fun initObservers() {
        loginModel.userObservable.subscribe {
            value -> loginResult(value)
        }

    }

    private fun updateUser() {
        uiState = uiState.copy(user = username.text.toString())
    }

    private fun updatePassword() {
        uiState = uiState.copy(password = password.text.toString())
    }

    private fun loginResult(user: User) {
        switchLoadingVisibility()
        this.user = user
        updateResult(user)
        if(!uiState.validUser) {
            showErrorMessage(uiState.error)
        }
    }

    private fun updateResult(user: User) {
        uiState = when(user) {
            is ClientUser -> uiState.copy(validUser = true, error = "")
            EmptyUser -> uiState.copy(validUser = false, error = "Fallo al iniciar sesión")
        }
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

    private fun showErrorMessage(errorString: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard(view: View) {
        (this@LoginViewActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun notifyRecoverAction() {
        onActionSubject.notify(LoginUiEvent.recoverPassword)
    }

    private fun notifySingInAction() {
        onActionSubject.notify(LoginUiEvent.SingIn)
    }










}
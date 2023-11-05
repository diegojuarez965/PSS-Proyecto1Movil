package mvc.view.home
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ospifakmobileversion.R
import com.google.android.material.navigation.NavigationView
import mvc.model.AppModel
import mvc.model.AppModelInjector
import mvc.model.entities.User
import mvc.view.login.LoginViewActivity
import observers.Observable
import observers.Subject

interface InicioView{
    val uiEventObservable: Observable<InicioUiEvent>
    var uiState:InicioUiState
}

class InicioViewActivity: InicioView, AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val onActionSubject = Subject<InicioUiEvent>()
    private lateinit var appModel: AppModel

    private lateinit var views: MutableList<View>
    private lateinit var activo: View
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var menuButton: ImageButton
    private lateinit var menuExpandedButton: ImageButton

    private lateinit var holaCliente: TextView
    private lateinit var planActual: TextView
    private lateinit var nombreCliente: TextView
    private lateinit var planCliente: TextView
    private lateinit var numCliente: TextView
    private lateinit var layoutCotitulares: ConstraintLayout

    private lateinit var nombre: TextView
    private lateinit var apellido: TextView
    private lateinit var plan: TextView
    private lateinit var nroCliente: TextView
    private lateinit var dni: TextView
    private lateinit var mail: TextView
    private lateinit var fechaNacimiento: TextView
    private lateinit var domicilio: TextView
    private lateinit var sexo: TextView

    override var uiState: InicioUiState = InicioUiState()
    override val uiEventObservable: Subject<InicioUiEvent> = onActionSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        initProperties()
        initListeners()
        initModule()
        initWindow()
    }

    private fun initProperties(){
        views = mutableListOf()
        views.add(findViewById(R.id.layout_inicio))
        views.add(findViewById(R.id.layout_perfil))
        activo = views[0]
        drawerLayout = findViewById(R.id.home_drawer_layout)

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.nav_inicio)

        menuButton = findViewById(R.id.menu_button)

        menuExpandedButton = navigationView.getHeaderView(0).findViewById(R.id.menu_expanded_button)

        holaCliente = findViewById(R.id.holaCliente)
        planActual = findViewById(R.id.planActual)
        nombreCliente = findViewById(R.id.nombreCliente)
        planCliente = findViewById(R.id.planCliente)
        numCliente = findViewById(R.id.numAfiliado)
        layoutCotitulares = findViewById(R.id.cotitulars_layaut)

        nombre = findViewById(R.id.nombre)
        apellido = findViewById(R.id.apellido)
        plan = findViewById(R.id.plan)
        nroCliente = findViewById(R.id.numero)
        dni = findViewById(R.id.dni)
        mail = findViewById(R.id.mail)
        fechaNacimiento = findViewById(R.id.fechaNacimiento)
        domicilio = findViewById(R.id.domicilio)
        sexo = findViewById(R.id.sexo)
    }

    private fun initModule(){
        appModel = AppModelInjector.getAppModel()
    }

    private fun initWindow(){
        updateStateInicioComponets(appModel.getClient())
        updateClienteInfoComponets()
        updateMiPerfilComponets()
    }

    private fun switchView(index: Int) {
        activo.visibility = INVISIBLE
        activo = views[index]
        activo.visibility = VISIBLE
    }

    private fun updateStateInicioComponets(user : User){
        (user as? User.ClientUser)?.let {
            uiState = uiState.copy(
                nombre = it.nombres,
                apellidos = it.apellidos,
                nro_afiliado = it.nro_afiliado,
                nombre_plan = it.nombre_plan,
                sexo = it.sexo,
                fechaNacimiento =it.fecha_nacimiento,
                telefono = it.telefono,
                domicilio = it.domicilio,
                dni = it.dni,
                email = it.email
            )
        }

    }
    private fun updateClienteInfoComponets(){
        runOnUiThread{
            holaCliente.text = "Hola, "+uiState.nombre+uiState.apellidos
            nombreCliente.text = uiState.nombre+" "+uiState.apellidos
            planCliente.text = uiState.nombre_plan
            numCliente.text = "Nro Afiliado:"+uiState.nro_afiliado
        }
    }

    private fun updateMiPerfilComponets(){
        runOnUiThread{
            nombre.text = "Nombre: "+uiState.nombre
            apellido.text = "Apellido: "+uiState.apellidos
            fechaNacimiento.text = "Fecha de nacimiento: "+uiState.fechaNacimiento
            plan.text = "Plan: "+uiState.nombre_plan
            nroCliente.text = "Nro Afiliado:"+uiState.nro_afiliado
            dni.text = "DNI: "+uiState.dni
            domicilio.text = "Domicilio: "+uiState.domicilio
            sexo.text = "Sexo: "+uiState.sexo
            mail.text = "MAIL: "+uiState.email
        }
    }

    private fun logOut() {
        val intent = Intent(this, LoginViewActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initListeners() {
        runOnUiThread {
            menuButton.setOnClickListener{
                drawerLayout.openDrawer(GravityCompat.END)
            }


            menuExpandedButton.setOnClickListener{
                drawerLayout.closeDrawer(GravityCompat.END)
            }
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_inicio -> switchView(0)
            R.id.nav_planes -> println("")
            R.id.nav_reintegros -> println("")
            R.id.nav_perfil -> switchView(1)
            R.id.nav_cerrarSesion -> logOut()
        }
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }
}
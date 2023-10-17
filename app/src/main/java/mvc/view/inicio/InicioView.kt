package mvc.view.inicio
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ospifakmobileversion.R
import mvc.model.AppModel
import mvc.model.AppModelInjector
import mvc.model.entities.User
import observers.Observable
import observers.Subject

interface InicioView{
    val uiEventObservable: Observable<InicioUiEvent>
    var uiState:InicioUiState
}

class InicioViewActivity: InicioView, AppCompatActivity() {

    private val onActionSubject = Subject<InicioUiEvent>()
    private lateinit var appModel: AppModel

    private lateinit var clienteInfoContenedor: View
    private lateinit var miPerfilContenedor: View

    private lateinit var inicioButton: Button
    private lateinit var planesButton: Button
    private lateinit var miPerfilButton: Button

    private lateinit var holaCliente: TextView
    private lateinit var planActual: TextView
    private lateinit var nombreCliente: TextView
    private lateinit var planCliente: TextView
    private lateinit var numCliente: TextView

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
        initModule()
        initListeners()
        initWindow()
    }

    private fun initProperties(){
        clienteInfoContenedor = findViewById(R.id.louyatClienteInfo)
        miPerfilContenedor = findViewById(R.id.louyatMiPerfil)

        inicioButton = findViewById(R.id.BotonInicio)
        planesButton = findViewById(R.id.BotonPlanes)
        miPerfilButton = findViewById(R.id.BotonPerfil)

        holaCliente = findViewById(R.id.holaCliente)
        planActual = findViewById(R.id.planActual)
        nombreCliente = findViewById(R.id.nombreCliente)
        planCliente = findViewById(R.id.planCliente)
        numCliente = findViewById(R.id.numAfiliado)

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

    private fun initListeners() {
        inicioButton.setOnClickListener {
            invisibleMiPerfil()
            visibleMiClienteInfo()
        }
        miPerfilButton.setOnClickListener {
            invisibleClienteInfo()
            visibleMiPerfil()
        }
    }

    private fun initModule(){
        appModel = AppModelInjector.getLoginModel()
    }

    private fun initWindow(){
        updateStateInicioComponets(appModel.getClient())
        invisibleMiPerfil()
        updateClienteInfoComponets()
        updateMiPerfilComponets()
    }

    private fun invisibleMiPerfil(){
        runOnUiThread {
            miPerfilContenedor.visibility = View.INVISIBLE
        }
    }

    private fun visibleMiPerfil(){
        runOnUiThread {
            miPerfilContenedor.visibility = View.VISIBLE
        }
    }

    private fun invisibleClienteInfo(){
        runOnUiThread {
            clienteInfoContenedor.visibility = View.INVISIBLE
        }
    }

    private fun visibleMiClienteInfo(){
        runOnUiThread {
            clienteInfoContenedor.visibility = View.VISIBLE
        }
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
            holaCliente.text = "Hola "+uiState.nombre+", "+uiState.apellidos
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

    private fun  getClienteNameFromIntent() = intent.getStringExtra(CLIENTE_NAME_EXTRA).toString()

    companion object{
        const val CLIENTE_NAME_EXTRA = "clienteName"
    }
}
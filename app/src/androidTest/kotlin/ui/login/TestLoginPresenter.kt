package ui.login

import android.util.Base64
import com.parkingreservation.iuh.demologinmvp.model.UserAccessToken
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import org.mockito.MockitoAnnotations
import org.junit.Before
import org.mockito.Mock
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginContract
import org.junit.Test
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import com.nhaarman.mockito_kotlin.verify
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginActivity
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginUtil

class TestLoginPresenter {


    var view: LoginContract.View = mock()

    var presenter: LoginPresenter = mock()

    var acti: LoginActivity = mock()

     var loginService: LoginService = mock()

    @Before
    @Throws(Exception::class)
    fun setup() {
    }

    @Test
    fun testLoginSuccess() {
        val base = LoginUtil.LOGIN_CLIENT + ":" + LoginUtil.LOGIN_PRIVATE
        val authHeader = "Basic " + Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP) // "Basic " required by Header

        val mockedResponse = UserAccessToken("1", "2", 1, "4")
        val string: String = ""
        doReturn(Observable.just(mockedResponse)).`when`(loginService).signIn(authHeader,"user71@gmail.com", "123")

        presenter.signIn("user71@gmail.com","123")
        verify(view).showLoading()
    }

    @Test
    fun testLoginFail() {
        val base = LoginUtil.LOGIN_CLIENT + ":" + LoginUtil.LOGIN_PRIVATE
        val authHeader = "Basic " + Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP) // "Basic " required by Header

        val mockedResponse = UserAccessToken("1", "2", 1, "4")
        val string: String = ""
        doReturn(Observable.just(mockedResponse)).`when`(loginService).signIn(authHeader,"user71@gmail.com", "321")
        verify(view).showError(string)
    }

}

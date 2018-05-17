package ui.login

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.parkingreservation.iuh.demologinmvp.model.Tickets
import com.parkingreservation.iuh.demologinmvp.model.UserAccessToken
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import com.parkingreservation.iuh.demologinmvp.service.TicketService
import com.parkingreservation.iuh.demologinmvp.ui.ticket.TicketContract
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TestTicketPresenter {

    @Mock
    lateinit var view: TicketContract.View

    @Mock
    lateinit var presenter: TicketContract.Presenter

    @Mock
    lateinit var service: TicketService

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun test_checked_ticket_successful() {

        val mockedResponse: Tickets = mock()
        val string: String = mock()

        doReturn(Observable.just(mockedResponse)).`when`(service).getCheckedTicket("","")
        verify(view).showSuccess(string)
    }

    @Test
    fun test_checked_ticket_fail() {

        val mockedResponse: Tickets = mock()
        val string: String = mock()

        doReturn(Observable.just(mockedResponse)).`when`(service).getCheckedTicket("","")
        verify(view).showSuccess(string)
    }

    @Test
    fun test_used_ticket_successful() {

        val mockedResponse: Tickets = mock()
        val string: String = mock()

        doReturn(Observable.just(mockedResponse)).`when`(service).getCheckedTicket("","")
        verify(view).showSuccess(string)
    }

    @Test
    fun test_used_ticket_fail() {

        val mockedResponse: Tickets = mock()
        val string: String = mock()

        doReturn(Observable.just(mockedResponse)).`when`(service).getCheckedTicket("","")
        verify(view).showSuccess(string)
    }

    @Test
    fun test_holding_ticket_successful() {

        val mockedResponse: Tickets = mock()
        val string: String = mock()

        doReturn(Observable.just(mockedResponse)).`when`(service).getCheckedTicket("","")
        verify(view).showSuccess(string)
    }
}
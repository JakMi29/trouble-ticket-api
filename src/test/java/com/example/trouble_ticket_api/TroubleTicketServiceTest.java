package com.example.trouble_ticket_api;

import com.example.trouble_ticket_api.business.dao.TroubleTicketDao;
import com.example.trouble_ticket_api.domain.exception.BadRequestException;
import com.example.trouble_ticket_api.domain.exception.NotFoundException;
import com.example.trouble_ticket_api.entity.Status;
import com.example.trouble_ticket_api.entity.TroubleTicket;
import com.example.trouble_ticket_api.mapper.TroubleTicketMapper;
import com.example.trouble_ticket_api.rest.dto.TroubleTicketDto;
import com.example.trouble_ticket_api.rest.dto.TroubleTicketSummary;
import com.example.trouble_ticket_api.rest.request.TroubleTicketCloseRequest;
import com.example.trouble_ticket_api.rest.request.TroubleTicketCreateRequest;
import com.example.trouble_ticket_api.service.TroubleTicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TroubleTicketServiceTest {

    @Mock
    private TroubleTicketDao dao;

    @Mock
    private TroubleTicketMapper mapper;

    @InjectMocks
    private TroubleTicketService service;

    @Test
    void shouldReturnExistingTicketWhenExternalIdExists() {
        // given
        String externalId = "EXT-123";
        TroubleTicketCreateRequest request = new TroubleTicketCreateRequest();
        request.setExternalId(externalId);

        TroubleTicket existingTicket = TroubleTicket.builder().id("1").externalId(externalId).build();
        TroubleTicketDto expectedDto = new TroubleTicketDto(
                "1", externalId, 1L, "Desc", Status.NEW, List.of()
        );

        when(dao.findByExternalId(externalId)).thenReturn(Optional.of(existingTicket));
        when(mapper.mapToDto(existingTicket)).thenReturn(expectedDto);

        // when
        TroubleTicketDto result = service.createOrGetTroubleTicket(request);

        // then
        assertThat(result).isSameAs(expectedDto);
        verify(dao, never()).save(any());
    }

    @Test
    void shouldCreateNewTicketWhenExternalIdDoesNotExist() {
        // given
        String externalId = "EXT-123";
        TroubleTicketCreateRequest request = new TroubleTicketCreateRequest();
        request.setExternalId(externalId);
        request.setServiceId(1L);
        request.setDescription("Test description");
        request.setNote("Initial note");

        when(dao.findByExternalId(externalId)).thenReturn(Optional.empty());

        TroubleTicketDto expectedDto = new TroubleTicketDto(
                "1", externalId, 1L, "Test description", Status.NEW, List.of()
        );
        when(mapper.mapToDto(any(TroubleTicket.class))).thenReturn(expectedDto);

        // when
        TroubleTicketDto result = service.createOrGetTroubleTicket(request);

        // then
        assertThat(result).isSameAs(expectedDto);

        ArgumentCaptor<TroubleTicket> captor = ArgumentCaptor.forClass(TroubleTicket.class);
        verify(dao).save(captor.capture());

        TroubleTicket saved = captor.getValue();
        assertThat(saved.getExternalId()).isEqualTo(externalId);
        assertThat(saved.getServiceId()).isEqualTo(1L);
        assertThat(saved.getDescription()).isEqualTo("Test description");
        assertThat(saved.getStatus()).isEqualTo(Status.NEW);
        assertThat(saved.getNotes()).hasSize(1);
        assertThat(saved.getNotes().get(0).getText()).isEqualTo("Initial note");
    }

    @Test
    void shouldListAllTroubleTickets() {
        // given
        TroubleTicket ticket = TroubleTicket.builder().id("1").build();
        TroubleTicketSummary summary = new TroubleTicketSummary("EXT-1", 1L, "Desc", Status.NEW);

        when(dao.findAll()).thenReturn(List.of(ticket));
        when(mapper.mapToSummary(ticket)).thenReturn(summary);

        // when
        List<TroubleTicketSummary> result = service.listTroubleTickets();

        // then
        assertThat(result).hasSize(1).containsExactly(summary);
    }

    @Test
    void shouldGetTroubleTicketDtoById() {
        // given
        String id = "1";
        TroubleTicket ticket = TroubleTicket.builder().id(id).build();
        TroubleTicketDto expectedDto = new TroubleTicketDto(
                id, "EXT-1", 1L, "Desc", Status.NEW, List.of()
        );

        when(dao.findById(id)).thenReturn(Optional.of(ticket));
        when(mapper.mapToDto(ticket)).thenReturn(expectedDto);

        // when
        TroubleTicketDto result = service.getTroubleTicketDtoById(id);

        // then
        assertThat(result).isSameAs(expectedDto);
    }

    @Test
    void shouldThrowNotFoundWhenGetDtoByIdNotFound() {
        // given
        String id = "999";
        when(dao.findById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> service.getTroubleTicketDtoById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Zgłoszenie nie istnieje albo nie jest widoczne w tenant scope użytkownika.");
    }

    @Test
    void shouldGetTroubleTicketEntityById() {
        // given
        String id = "1";
        TroubleTicket ticket = TroubleTicket.builder().id(id).build();

        when(dao.findById(id)).thenReturn(Optional.of(ticket));

        // when
        TroubleTicket result = service.getTroubleTicketById(id);

        // then
        assertThat(result).isSameAs(ticket);
    }

    @Test
    void shouldThrowNotFoundWhenGetEntityByIdNotFound() {
        // given
        String id = "999";
        when(dao.findById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> service.getTroubleTicketById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Zgłoszenie nie istnieje albo nie jest widoczne w tenant scope użytkownika.");
    }

    @Test
    void shouldCloseTroubleTicketSuccessfully() {
        // given
        String id = "1";
        TroubleTicket ticket = TroubleTicket.builder().id(id).status(Status.NEW).build();
        TroubleTicketCloseRequest request = new TroubleTicketCloseRequest(Status.CLOSED);
        TroubleTicketDto expectedDto = new TroubleTicketDto(
                id, "EXT-1", 1L, "Desc", Status.CLOSED, List.of()
        );

        when(dao.findById(id)).thenReturn(Optional.of(ticket));
        when(mapper.mapToDto(ticket)).thenReturn(expectedDto);

        // when
        TroubleTicketDto result = service.closeTroubleTicket(id, request);

        // then
        assertThat(result).isSameAs(expectedDto);
        assertThat(ticket.getStatus()).isEqualTo(Status.CLOSED);
        verify(dao).save(ticket);
    }

    @Test
    void shouldThrowNotFoundWhenClosingNonExistentTicket() {
        // given
        String id = "999";
        TroubleTicketCloseRequest request = new TroubleTicketCloseRequest(Status.CLOSED);

        when(dao.findById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> service.closeTroubleTicket(id, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Zgłoszenie nie istnieje albo nie jest widoczne w tenant scope użytkownika.");
    }

    @Test
    void shouldThrowBadRequestWhenClosingWithInvalidStatus() {
        // given
        String id = "1";
        TroubleTicket ticket = TroubleTicket.builder().id(id).status(Status.NEW).build();
        TroubleTicketCloseRequest request = new TroubleTicketCloseRequest(Status.NEW);

        when(dao.findById(id)).thenReturn(Optional.of(ticket));

        // when / then
        assertThatThrownBy(() -> service.closeTroubleTicket(id, request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Pole status ma niedozwoloną wartość dla tej operacji.");

        verify(dao, never()).save(any());
    }
}
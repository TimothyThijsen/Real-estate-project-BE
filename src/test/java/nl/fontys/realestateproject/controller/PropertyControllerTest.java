package nl.fontys.realestateproject.controller;

import nl.fontys.realestateproject.business.dto.property.CreatePropertyResponse;
import nl.fontys.realestateproject.business.dto.property.GetAllPropertiesResponse;
import nl.fontys.realestateproject.business.impl.PropertyServiceImpl;
import nl.fontys.realestateproject.configuration.security.auth.RequestAuthenticatedUserProvider;
import nl.fontys.realestateproject.domain.Account;
import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;
import nl.fontys.realestateproject.domain.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PropertyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RequestAuthenticatedUserProvider requestAuthenticatedUserProvider;
    @MockBean
    PropertyServiceImpl propertyService;

    @Test
    @WithMockUser(username = "test@mail.com", roles = {"ADMIN"})
    void createProperty_shouldReturn201ResponseWithCreatedProperty() throws Exception {
        // Arrange
        CreatePropertyResponse response = CreatePropertyResponse.builder().propertyId(1L).build();
        when(propertyService.createProperty(any())).thenReturn(response);
        // Act
        mockMvc.perform(post("/properties")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "description": "Nice room located near centrum.",
                                    "price": 1200,
                                    "propertyType": "RESIDENTIAL",
                                    "listingType": "RENTAL",
                                    "surfaceAreas": [
                                        {
                                            "nameOfSurfaceArea": "Living Area",
                                            "areaInSquareMetre": 18
                                        },
                                        {
                                            "nameOfSurfaceArea": "Room 1",
                                            "areaInSquareMetre": 8
                                        }
                                    ],
                                    "street": "Boschdijk 4D",
                                    "city": "Eindhoven",
                                    "postalCode": "5612AB",
                                    "country": "The netherlands",
                                    "agentId": 1,
                                    "imageUrl": null
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {
                            "propertyId": 1
                        }
                        """));


        // Assert
        verify(propertyService).createProperty(any());
    }

    @Test
    void GetAllProperties_shouldReturn200ResponseWithAllProperties() throws Exception {
        // Arrange
        GetAllPropertiesResponse response = GetAllPropertiesResponse.builder()
                .properties(List.of(
                        Property.builder()
                                .id(1)
                                .description("Nice room located near centrum.")
                                .price(BigDecimal.valueOf(1200))
                                .propertyType(PropertyType.RESIDENTIAL)
                                .listingType(ListingType.RENTAL)
                                .surfaceAreas(List.of(
                                        PropertySurfaceArea.builder()
                                                .nameOfSurfaceArea("Living Area")
                                                .areaInSquareMetre(18.0)
                                                .build()
                                ))
                                .address(Address.builder()
                                    .street("Boschdijk 4D")
                                    .city("Eindhoven")
                                    .postalCode("5612AB")
                                    .country("The netherlands")
                                .build())
                                .agent(Account.builder().id(1L).roles(List.of(UserRole.AGENT)).email("agent@mail.com").lastName("agent").firstName("agent").build())
                                .imageUrl(null)
                                .build()
                ))
                .build();
        when(propertyService.getAllProperties()).thenReturn(response);
        mockMvc.perform(get("/properties"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "properties": [
                                    {
                                        "id": 1,
                                        "description": "Nice room located near centrum.",
                                        "price": 1200,
                                        "listingType": "RENTAL",
                                        "propertyType": "RESIDENTIAL",
                                        "surfaceAreas": [
                                            {
                                                "nameOfSurfaceArea": "Living Area",
                                                "areaInSquareMetre": 18.0
                                            }
                                        ],
                                        "address": {
                                            "street": "Boschdijk 4D",
                                            "city": "Eindhoven",
                                            "postalCode": "5612AB",
                                            "country": "The netherlands"
                                        },
                                        "agent": {
                                            "id": 1,
                                            "email": "agent@mail.com",
                                            "firstName": "agent",
                                            "lastName": "agent",
                                            "roles": ["AGENT"]
                                        },
                                        "imageUrl": null
                                    }
                                ]
                            }
                        """));
        verify(propertyService).getAllProperties();
    }
}

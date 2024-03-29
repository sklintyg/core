package se.inera.intyg.certificateservice.application.common.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;

class ResourceLinkTypeDTOTest {

    @Nested
    class ActionType {

        @Test
        void shallReturnCertificateActionTypeCreate() {
            assertEquals(CertificateActionType.CREATE,
                    ResourceLinkTypeDTO.CREATE_CERTIFICATE.toCertificateActionType()
            );
        }

        @Test
        void shallReturnCertificateActionTypeRead() {
            assertEquals(CertificateActionType.READ,
                    ResourceLinkTypeDTO.READ_CERTIFICATE.toCertificateActionType()
            );
        }

        @Test
        void shallReturnCertificateActionTypeUpdate() {
            assertEquals(CertificateActionType.UPDATE,
                    ResourceLinkTypeDTO.EDIT_CERTIFICATE.toCertificateActionType()
            );
        }

        @Test
        void shallReturnCertificateActionTypeDelete() {
            assertEquals(CertificateActionType.DELETE,
                    ResourceLinkTypeDTO.REMOVE_CERTIFICATE.toCertificateActionType()
            );
        }

        @Test
        void shallReturnCertificateActionTypeSign() {
            assertEquals(CertificateActionType.SIGN,
                    ResourceLinkTypeDTO.SIGN_CERTIFICATE.toCertificateActionType()
            );
        }

        @Test
        void shallReturnCertificateActionTypeSend() {
            assertEquals(CertificateActionType.SEND,
                    ResourceLinkTypeDTO.SEND_CERTIFICATE.toCertificateActionType()
            );
        }

        @Test
        void shallThrowExceptionForNotSupportedType() {
            assertThrows(IllegalArgumentException.class,
                    ResourceLinkTypeDTO.SRS_FULL_VIEW::toCertificateActionType
            );
        }

        @Test
        void shallReturnCertificateActionTypePrint() {
            assertEquals(CertificateActionType.PRINT,
                    ResourceLinkTypeDTO.PRINT_CERTIFICATE.toCertificateActionType()
            );
        }

        @Nested
        class ResourceLinkType {

            @Test
            void shallReturnResourceLinkTypeDTOCreateCertificate() {
                assertEquals(ResourceLinkTypeDTO.CREATE_CERTIFICATE,
                        ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.CREATE)
                );
            }

            @Test
            void shallReturnResourceLinkTypeDTOReadCertificate() {
                assertEquals(ResourceLinkTypeDTO.READ_CERTIFICATE,
                        ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.READ)
                );
            }

            @Test
            void shallReturnResourceLinkTypeDTOEditCertificate() {
                assertEquals(ResourceLinkTypeDTO.EDIT_CERTIFICATE,
                        ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.UPDATE)
                );
            }

            @Test
            void shallReturnResourceLinkTypeDTORemoveCertificate() {
                assertEquals(ResourceLinkTypeDTO.REMOVE_CERTIFICATE,
                        ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.DELETE)
                );
            }

            @Test
            void shallReturnResourceLinkTypeDTOSignCertificate() {
                assertEquals(ResourceLinkTypeDTO.SIGN_CERTIFICATE,
                        ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SIGN)
                );
            }

            @Test
            void shallReturnResourceLinkTypeDTOPrintCertificate() {
                assertEquals(ResourceLinkTypeDTO.PRINT_CERTIFICATE,
                        ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.PRINT)
                );
            }

            @Test
            void shallReturnResourceLinkTypeDTOSendCertificate() {
                assertEquals(ResourceLinkTypeDTO.SEND_CERTIFICATE,
                        ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SEND)
                );
            }
        }
    }
}


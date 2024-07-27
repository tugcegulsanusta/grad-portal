import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './graduation.reducer';

export const GraduationDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const graduationEntity = useAppSelector(state => state.graduation.entity);
  const updateSuccess = useAppSelector(state => state.graduation.updateSuccess);

  const handleClose = () => {
    navigate('/graduation' + pageLocation.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(graduationEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="graduationDeleteDialogHeading">
        Silme işlemini onayla
      </ModalHeader>
      <ModalBody id="gradPortalApp.graduation.delete.question">
        {graduationEntity.id} nolu Graduation silinecek. Devem etmek istiyor musunuz?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; İptal
        </Button>
        <Button id="jhi-confirm-delete-graduation" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Sil
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default GraduationDeleteDialog;

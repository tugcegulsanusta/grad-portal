import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './job-history.reducer';

export const JobHistoryDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const jobHistoryEntity = useAppSelector(state => state.jobHistory.entity);
  const updateSuccess = useAppSelector(state => state.jobHistory.updateSuccess);

  const handleClose = () => {
    navigate('/job-history' + pageLocation.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(jobHistoryEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="jobHistoryDeleteDialogHeading">
        Silme işlemini onayla
      </ModalHeader>
      <ModalBody id="gradPortalApp.jobHistory.delete.question">
        {jobHistoryEntity.id} nolu Job History silinecek. Devem etmek istiyor musunuz?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; İptal
        </Button>
        <Button id="jhi-confirm-delete-jobHistory" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Sil
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default JobHistoryDeleteDialog;

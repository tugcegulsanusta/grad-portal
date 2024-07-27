import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGrad } from 'app/shared/model/grad.model';
import { getEntity, updateEntity, createEntity, reset } from './grad.reducer';

export const GradUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gradEntity = useAppSelector(state => state.grad.entity);
  const loading = useAppSelector(state => state.grad.loading);
  const updating = useAppSelector(state => state.grad.updating);
  const updateSuccess = useAppSelector(state => state.grad.updateSuccess);

  const handleClose = () => {
    navigate('/grad' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...gradEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...gradEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gradPortalApp.grad.home.createOrEditLabel" data-cy="GradCreateUpdateHeading">
            Yeni Grad ekle veya guncelle
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="grad-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="First Name" id="grad-firstName" name="firstName" data-cy="firstName" type="text" />
              <ValidatedField label="Last Name" id="grad-lastName" name="lastName" data-cy="lastName" type="text" />
              <ValidatedField label="Email" id="grad-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Phone Number" id="grad-phoneNumber" name="phoneNumber" data-cy="phoneNumber" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/grad" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Geri</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Kaydet
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default GradUpdate;

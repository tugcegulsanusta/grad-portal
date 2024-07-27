import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISchool } from 'app/shared/model/school.model';
import { getEntity, updateEntity, createEntity, reset } from './school.reducer';

export const SchoolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const schoolEntity = useAppSelector(state => state.school.entity);
  const loading = useAppSelector(state => state.school.loading);
  const updating = useAppSelector(state => state.school.updating);
  const updateSuccess = useAppSelector(state => state.school.updateSuccess);

  const handleClose = () => {
    navigate('/school' + location.search);
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
      ...schoolEntity,
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
          ...schoolEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gradPortalApp.school.home.createOrEditLabel" data-cy="SchoolCreateUpdateHeading">
            Yeni School ekle veya guncelle
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="school-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="University Name"
                id="school-universityName"
                name="universityName"
                data-cy="universityName"
                type="text"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                }}
              />
              <ValidatedField
                label="Faculty Name"
                id="school-facultyName"
                name="facultyName"
                data-cy="facultyName"
                type="text"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                }}
              />
              <ValidatedField
                label="Department Name"
                id="school-departmentName"
                name="departmentName"
                data-cy="departmentName"
                type="text"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/school" replace color="info">
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

export default SchoolUpdate;

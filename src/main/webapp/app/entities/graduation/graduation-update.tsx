import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { IGrad } from 'app/shared/model/grad.model';
import { getEntities as getGrads } from 'app/entities/grad/grad.reducer';
import { IGraduation } from 'app/shared/model/graduation.model';
import { getEntity, updateEntity, createEntity, reset } from './graduation.reducer';

export const GraduationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const schools = useAppSelector(state => state.school.entities);
  const grads = useAppSelector(state => state.grad.entities);
  const graduationEntity = useAppSelector(state => state.graduation.entity);
  const loading = useAppSelector(state => state.graduation.loading);
  const updating = useAppSelector(state => state.graduation.updating);
  const updateSuccess = useAppSelector(state => state.graduation.updateSuccess);

  const handleClose = () => {
    navigate('/graduation' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSchools({}));
    dispatch(getGrads({}));
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
    if (values.startYear !== undefined && typeof values.startYear !== 'number') {
      values.startYear = Number(values.startYear);
    }
    if (values.graduationYear !== undefined && typeof values.graduationYear !== 'number') {
      values.graduationYear = Number(values.graduationYear);
    }
    if (values.gpa !== undefined && typeof values.gpa !== 'number') {
      values.gpa = Number(values.gpa);
    }

    const entity = {
      ...graduationEntity,
      ...values,
      schoolId: schools.find(it => it.id.toString() === values.schoolId?.toString()),
      gradId: grads.find(it => it.id.toString() === values.gradId?.toString()),
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
          ...graduationEntity,
          schoolId: graduationEntity?.schoolId?.id,
          gradId: graduationEntity?.gradId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gradPortalApp.graduation.home.createOrEditLabel" data-cy="GraduationCreateUpdateHeading">
            Yeni Graduation ekle veya guncelle
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="graduation-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Start Year" id="graduation-startYear" name="startYear" data-cy="startYear" type="text" />
              <ValidatedField
                label="Graduation Year"
                id="graduation-graduationYear"
                name="graduationYear"
                data-cy="graduationYear"
                type="text"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                  validate: v => isNumber(v) || 'Bu alan numara içermeli.',
                }}
              />
              <ValidatedField label="Gpa" id="graduation-gpa" name="gpa" data-cy="gpa" type="text" />
              <ValidatedField id="graduation-schoolId" name="schoolId" data-cy="schoolId" label="School Id" type="select">
                <option value="" key="0" />
                {schools
                  ? schools.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="graduation-gradId" name="gradId" data-cy="gradId" label="Grad Id" type="select">
                <option value="" key="0" />
                {grads
                  ? grads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/graduation" replace color="info">
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

export default GraduationUpdate;

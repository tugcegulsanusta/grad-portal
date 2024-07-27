import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGrad } from 'app/shared/model/grad.model';
import { getEntities as getGrads } from 'app/entities/grad/grad.reducer';
import { IJobHistory } from 'app/shared/model/job-history.model';
import { getEntity, updateEntity, createEntity, reset } from './job-history.reducer';

export const JobHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const grads = useAppSelector(state => state.grad.entities);
  const jobHistoryEntity = useAppSelector(state => state.jobHistory.entity);
  const loading = useAppSelector(state => state.jobHistory.loading);
  const updating = useAppSelector(state => state.jobHistory.updating);
  const updateSuccess = useAppSelector(state => state.jobHistory.updateSuccess);

  const handleClose = () => {
    navigate('/job-history' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...jobHistoryEntity,
      ...values,
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
      ? {
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...jobHistoryEntity,
          startDate: convertDateTimeFromServer(jobHistoryEntity.startDate),
          endDate: convertDateTimeFromServer(jobHistoryEntity.endDate),
          gradId: jobHistoryEntity?.gradId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gradPortalApp.jobHistory.home.createOrEditLabel" data-cy="JobHistoryCreateUpdateHeading">
            Yeni Job History ekle veya guncelle
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="job-history-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Company Name"
                id="job-history-companyName"
                name="companyName"
                data-cy="companyName"
                type="text"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                }}
              />
              <ValidatedField
                label="Job Title"
                id="job-history-jobTitle"
                name="jobTitle"
                data-cy="jobTitle"
                type="text"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                }}
              />
              <ValidatedField
                label="Start Date"
                id="job-history-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                }}
              />
              <ValidatedField
                label="End Date"
                id="job-history-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Is Current" id="job-history-isCurrent" name="isCurrent" data-cy="isCurrent" check type="checkbox" />
              <ValidatedField id="job-history-gradId" name="gradId" data-cy="gradId" label="Grad Id" type="select">
                <option value="" key="0" />
                {grads
                  ? grads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-history" replace color="info">
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

export default JobHistoryUpdate;

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
import { ISkill } from 'app/shared/model/skill.model';
import { getEntity, updateEntity, createEntity, reset } from './skill.reducer';

export const SkillUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const grads = useAppSelector(state => state.grad.entities);
  const skillEntity = useAppSelector(state => state.skill.entity);
  const loading = useAppSelector(state => state.skill.loading);
  const updating = useAppSelector(state => state.skill.updating);
  const updateSuccess = useAppSelector(state => state.skill.updateSuccess);

  const handleClose = () => {
    navigate('/skill' + location.search);
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
    if (values.rate !== undefined && typeof values.rate !== 'number') {
      values.rate = Number(values.rate);
    }
    if (values.order !== undefined && typeof values.order !== 'number') {
      values.order = Number(values.order);
    }

    const entity = {
      ...skillEntity,
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
      ? {}
      : {
          ...skillEntity,
          gradId: skillEntity?.gradId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gradPortalApp.skill.home.createOrEditLabel" data-cy="SkillCreateUpdateHeading">
            Yeni Skill ekle veya guncelle
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="skill-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="skill-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Bu alan gereklidir.' },
                }}
              />
              <ValidatedField label="Description" id="skill-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Rate" id="skill-rate" name="rate" data-cy="rate" type="text" />
              <ValidatedField label="Order" id="skill-order" name="order" data-cy="order" type="text" />
              <ValidatedField id="skill-gradId" name="gradId" data-cy="gradId" label="Grad Id" type="select">
                <option value="" key="0" />
                {grads
                  ? grads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/skill" replace color="info">
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

export default SkillUpdate;

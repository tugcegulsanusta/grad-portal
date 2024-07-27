import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './graduation.reducer';

export const GraduationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const graduationEntity = useAppSelector(state => state.graduation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="graduationDetailsHeading">Graduation</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{graduationEntity.id}</dd>
          <dt>
            <span id="startYear">Start Year</span>
          </dt>
          <dd>{graduationEntity.startYear}</dd>
          <dt>
            <span id="graduationYear">Graduation Year</span>
          </dt>
          <dd>{graduationEntity.graduationYear}</dd>
          <dt>
            <span id="gpa">Gpa</span>
          </dt>
          <dd>{graduationEntity.gpa}</dd>
          <dt>School Id</dt>
          <dd>{graduationEntity.schoolId ? graduationEntity.schoolId.id : ''}</dd>
          <dt>Grad Id</dt>
          <dd>{graduationEntity.gradId ? graduationEntity.gradId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/graduation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Geri</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/graduation/${graduationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Düzenle</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GraduationDetail;

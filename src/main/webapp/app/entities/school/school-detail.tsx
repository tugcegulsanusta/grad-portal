import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './school.reducer';

export const SchoolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const schoolEntity = useAppSelector(state => state.school.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="schoolDetailsHeading">School</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{schoolEntity.id}</dd>
          <dt>
            <span id="universityName">University Name</span>
          </dt>
          <dd>{schoolEntity.universityName}</dd>
          <dt>
            <span id="facultyName">Faculty Name</span>
          </dt>
          <dd>{schoolEntity.facultyName}</dd>
          <dt>
            <span id="departmentName">Department Name</span>
          </dt>
          <dd>{schoolEntity.departmentName}</dd>
        </dl>
        <Button tag={Link} to="/school" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Geri</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/school/${schoolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Düzenle</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SchoolDetail;

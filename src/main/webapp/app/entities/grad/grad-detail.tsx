import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './grad.reducer';

export const GradDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gradEntity = useAppSelector(state => state.grad.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gradDetailsHeading">Grad</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gradEntity.id}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{gradEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{gradEntity.lastName}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{gradEntity.email}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{gradEntity.phoneNumber}</dd>
        </dl>
        <Button tag={Link} to="/grad" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Geri</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grad/${gradEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Düzenle</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GradDetail;

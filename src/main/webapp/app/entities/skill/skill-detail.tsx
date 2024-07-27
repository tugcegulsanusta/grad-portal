import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './skill.reducer';

export const SkillDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const skillEntity = useAppSelector(state => state.skill.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="skillDetailsHeading">Skill</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{skillEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{skillEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{skillEntity.description}</dd>
          <dt>
            <span id="rate">Rate</span>
          </dt>
          <dd>{skillEntity.rate}</dd>
          <dt>
            <span id="order">Order</span>
          </dt>
          <dd>{skillEntity.order}</dd>
          <dt>Grad Id</dt>
          <dd>{skillEntity.gradId ? skillEntity.gradId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/skill" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Geri</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/skill/${skillEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Düzenle</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SkillDetail;

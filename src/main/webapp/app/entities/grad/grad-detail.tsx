import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity, getProfileEntity } from './grad.reducer';
import { Radio, Timeline } from 'antd';
import { Badge, Card, Space } from 'antd';

export const GradDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();
  const [graduationList, setGraduationList] = useState([]);
  const [skillList, setSkillList] = useState([]);
  useEffect(() => {
    getProfileEntity(id)
      .then(function (response) {
        // handle success
        if (response && response.data) {
          setSkillList(response.data.skillList);
          setGraduationList(response.data.graduationList);
          console.log('response.data', response.data);
        }
      })
      .catch(function (error) {
        // handle error
        console.log('error', error);
      });
    dispatch(getEntity(id));
  }, []);

  const gradEntity = useAppSelector(state => state.grad.entity);

  const renderSkillCard = (value: any, index: number, array: any[]) => {
    const insideElement = (
      <Card title={value?.name} size="small">
        {value?.description}
      </Card>
    );
    if (value?.rate >= 0.0 && value?.rate <= 5.0) {
      if (Math.abs(value?.rate - 5) < 0.1) {
        return <Badge.Ribbon text="&#9733;&#9733;&#9733;&#9733;&#9733;"> {insideElement} </Badge.Ribbon>;
      } else if (Math.abs(value?.rate - 4) < 0.1) {
        return <Badge.Ribbon text="&#9733;&#9733;&#9733;&#9733;"> {insideElement} </Badge.Ribbon>;
      } else if (Math.abs(value?.rate - 3) < 0.1) {
        return <Badge.Ribbon text="&#9733;&#9733;&#9733;"> {insideElement} </Badge.Ribbon>;
      } else if (Math.abs(value?.rate - 2) < 0.1) {
        return <Badge.Ribbon text="&#9733;&#9733;"> {insideElement} </Badge.Ribbon>;
      } else if (Math.abs(value?.rate - 1) < 0.1) {
        return <Badge.Ribbon text="&#9733;"> {insideElement} </Badge.Ribbon>;
      }
    }
    return <Badge.Ribbon text={value?.rate}> {insideElement} </Badge.Ribbon>;
  };
  return (
    <Row>
      <Col md="4">
        <h2 data-cy="gradDetailsHeading">
          {gradEntity.firstName} {gradEntity.lastName}
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gradEntity.id}</dd>
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
      <Col md="4">
        <h2 data-cy="gradDetailsHeading">Graduation & Career</h2>
        <Timeline
          mode={'left'}
          items={[
            {
              label: '2015-09-01',
              children: 'Create a services',
            },
            {
              label: '2015-09-01 09:12:11',
              children: 'Solve initial network problems',
            },
            {
              children: 'Technical testing',
            },
            {
              label: '2015-09-01 09:12:11',
              children: 'Network problems being solved',
            },
          ]}
        />
      </Col>
      <Col md="4">
        <h2 data-cy="gradDetailsHeading">Skills</h2>
        <Space direction="vertical" size="middle" style={{ width: '100%' }}>
          {skillList.map(renderSkillCard)}
        </Space>
      </Col>
    </Row>
  );
};

export default GradDetail;

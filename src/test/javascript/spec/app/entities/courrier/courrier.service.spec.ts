import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CourrierService } from 'app/entities/courrier/courrier.service';
import { ICourrier, Courrier } from 'app/shared/model/courrier.model';
import { TypeCourrier } from 'app/shared/model/enumerations/type-courrier.model';
import { Status } from 'app/shared/model/enumerations/status.model';

describe('Service Tests', () => {
  describe('Courrier Service', () => {
    let injector: TestBed;
    let service: CourrierService;
    let httpMock: HttpTestingController;
    let elemDefault: ICourrier;
    let expectedResult: ICourrier | ICourrier[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CourrierService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Courrier(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        TypeCourrier.Arrivee,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        false,
        false,
        currentDate,
        currentDate,
        Status.Ouvert,
        'image/png',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT),
            receivedAt: currentDate.format(DATE_TIME_FORMAT),
            sentAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Courrier', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT),
            receivedAt: currentDate.format(DATE_TIME_FORMAT),
            sentAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
            dateAccuse: currentDate,
            dateReponse: currentDate,
            receivedAt: currentDate,
            sentAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Courrier()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Courrier', () => {
        const returnedFromService = Object.assign(
          {
            idCourrier: 'BBBBBB',
            subject: 'BBBBBB',
            description: 'BBBBBB',
            typeCourrier: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedBy: 'BBBBBB',
            delai: 1,
            relance: 1,
            accuse: true,
            reponse: true,
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            data: 'BBBBBB',
            receivedAt: currentDate.format(DATE_TIME_FORMAT),
            instruction: 'BBBBBB',
            expediteurDesc: 'BBBBBB',
            sentAt: currentDate.format(DATE_TIME_FORMAT),
            destinataireDesc: 'BBBBBB',
            destinataireVille: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
            dateAccuse: currentDate,
            dateReponse: currentDate,
            receivedAt: currentDate,
            sentAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Courrier', () => {
        const returnedFromService = Object.assign(
          {
            idCourrier: 'BBBBBB',
            subject: 'BBBBBB',
            description: 'BBBBBB',
            typeCourrier: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'BBBBBB',
            updatedBy: 'BBBBBB',
            delai: 1,
            relance: 1,
            accuse: true,
            reponse: true,
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            data: 'BBBBBB',
            receivedAt: currentDate.format(DATE_TIME_FORMAT),
            instruction: 'BBBBBB',
            expediteurDesc: 'BBBBBB',
            sentAt: currentDate.format(DATE_TIME_FORMAT),
            destinataireDesc: 'BBBBBB',
            destinataireVille: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
            dateAccuse: currentDate,
            dateReponse: currentDate,
            receivedAt: currentDate,
            sentAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Courrier', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

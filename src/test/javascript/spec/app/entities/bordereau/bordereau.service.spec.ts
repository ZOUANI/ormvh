/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BordereauService } from 'app/entities/bordereau/bordereau.service';
import { IBordereau, Bordereau } from 'app/shared/model/bordereau.model';

describe('Service Tests', () => {
  describe('Bordereau Service', () => {
    let injector: TestBed;
    let service: BordereauService;
    let httpMock: HttpTestingController;
    let elemDefault: IBordereau;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BordereauService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Bordereau(0, 'AAAAAAA', 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateBordereaux: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Bordereau', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateBordereaux: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateBordereaux: currentDate
          },
          returnedFromService
        );
        service
          .create(new Bordereau(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Bordereau', async () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            description: 'BBBBBB',
            nombrePieceJointe: 1,
            dateBordereaux: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBordereaux: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Bordereau', async () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            description: 'BBBBBB',
            nombrePieceJointe: 1,
            dateBordereaux: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateBordereaux: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Bordereau', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

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

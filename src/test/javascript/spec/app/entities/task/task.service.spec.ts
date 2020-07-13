/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TaskService } from 'app/entities/task/task.service';
import { ITask, Task, Status } from 'app/shared/model/task.model';

describe('Service Tests', () => {
  describe('Task Service', () => {
    let injector: TestBed;
    let service: TaskService;
    let httpMock: HttpTestingController;
    let elemDefault: ITask;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TaskService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Task(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        Status.Ouvert,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        false,
        false,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            assignedAt: currentDate.format(DATE_TIME_FORMAT),
            processedAt: currentDate.format(DATE_TIME_FORMAT),
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT)
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

      it('should create a Task', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            assignedAt: currentDate.format(DATE_TIME_FORMAT),
            processedAt: currentDate.format(DATE_TIME_FORMAT),
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
            assignedAt: currentDate,
            processedAt: currentDate,
            dateAccuse: currentDate,
            dateReponse: currentDate
          },
          returnedFromService
        );
        service
          .create(new Task(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Task', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            assignedAt: currentDate.format(DATE_TIME_FORMAT),
            processedAt: currentDate.format(DATE_TIME_FORMAT),
            observation: 'BBBBBB',
            status: 'BBBBBB',
            createdBy: 'BBBBBB',
            updatedBy: 'BBBBBB',
            delai: 1,
            relance: 1,
            accuse: true,
            reponse: true,
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
            assignedAt: currentDate,
            processedAt: currentDate,
            dateAccuse: currentDate,
            dateReponse: currentDate
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

      it('should return a list of Task', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            assignedAt: currentDate.format(DATE_TIME_FORMAT),
            processedAt: currentDate.format(DATE_TIME_FORMAT),
            observation: 'BBBBBB',
            status: 'BBBBBB',
            createdBy: 'BBBBBB',
            updatedBy: 'BBBBBB',
            delai: 1,
            relance: 1,
            accuse: true,
            reponse: true,
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
            assignedAt: currentDate,
            processedAt: currentDate,
            dateAccuse: currentDate,
            dateReponse: currentDate
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

      it('should delete a Task', async () => {
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

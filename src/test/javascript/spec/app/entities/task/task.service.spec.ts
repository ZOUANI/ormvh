import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TaskService } from 'app/entities/task/task.service';
import { ITask, Task } from 'app/shared/model/task.model';
import { Status } from 'app/shared/model/enumerations/status.model';

describe('Service Tests', () => {
  describe('Task Service', () => {
    let injector: TestBed;
    let service: TaskService;
    let httpMock: HttpTestingController;
    let elemDefault: ITask;
    let expectedResult: ITask | ITask[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
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
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            assignedAt: currentDate.format(DATE_TIME_FORMAT),
            processedAt: currentDate.format(DATE_TIME_FORMAT),
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Task', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            assignedAt: currentDate.format(DATE_TIME_FORMAT),
            processedAt: currentDate.format(DATE_TIME_FORMAT),
            dateAccuse: currentDate.format(DATE_FORMAT),
            dateReponse: currentDate.format(DATE_FORMAT),
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
            dateReponse: currentDate,
          },
          returnedFromService
        );

        service.create(new Task()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Task', () => {
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
            dateReponse: currentDate.format(DATE_FORMAT),
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
            dateReponse: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Task', () => {
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
            dateReponse: currentDate.format(DATE_FORMAT),
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
            dateReponse: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Task', () => {
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

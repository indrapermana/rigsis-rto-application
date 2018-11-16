/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WellService } from 'app/entities/well/well.service';
import { IWell, Well, Status } from 'app/shared/model/well.model';

describe('Service Tests', () => {
    describe('Well Service', () => {
        let injector: TestBed;
        let service: WellService;
        let httpMock: HttpTestingController;
        let elemDefault: IWell;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(WellService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Well(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                Status.Active,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                0,
                0,
                0,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        licenseDate: currentDate.format(DATE_TIME_FORMAT),
                        dateTimeSpud: currentDate.format(DATE_TIME_FORMAT),
                        dateTimePA: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Well', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        licenseDate: currentDate.format(DATE_TIME_FORMAT),
                        dateTimeSpud: currentDate.format(DATE_TIME_FORMAT),
                        dateTimePA: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        licenseDate: currentDate,
                        dateTimeSpud: currentDate,
                        dateTimePA: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Well(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Well', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        legalName: 'BBBBBB',
                        licenseDate: currentDate.format(DATE_TIME_FORMAT),
                        licenseNumber: 'BBBBBB',
                        govermentNumber: 'BBBBBB',
                        apiNumber: 'BBBBBB',
                        country: 'BBBBBB',
                        block: 'BBBBBB',
                        field: 'BBBBBB',
                        district: 'BBBBBB',
                        county: 'BBBBBB',
                        state: 'BBBBBB',
                        region: 'BBBBBB',
                        operator: 'BBBBBB',
                        operatorInterest: 1,
                        operatorDivision: 'BBBBBB',
                        timeZone: 'BBBBBB',
                        status: 'BBBBBB',
                        purpose: 'BBBBBB',
                        direction: 'BBBBBB',
                        fluid: 'BBBBBB',
                        dateTimeSpud: currentDate.format(DATE_TIME_FORMAT),
                        dateTimePA: currentDate.format(DATE_TIME_FORMAT),
                        headElevation: 1,
                        groundElevation: 1,
                        waterDepth: 1,
                        latitude: 'BBBBBB',
                        longitude: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        licenseDate: currentDate,
                        dateTimeSpud: currentDate,
                        dateTimePA: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Well', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        legalName: 'BBBBBB',
                        licenseDate: currentDate.format(DATE_TIME_FORMAT),
                        licenseNumber: 'BBBBBB',
                        govermentNumber: 'BBBBBB',
                        apiNumber: 'BBBBBB',
                        country: 'BBBBBB',
                        block: 'BBBBBB',
                        field: 'BBBBBB',
                        district: 'BBBBBB',
                        county: 'BBBBBB',
                        state: 'BBBBBB',
                        region: 'BBBBBB',
                        operator: 'BBBBBB',
                        operatorInterest: 1,
                        operatorDivision: 'BBBBBB',
                        timeZone: 'BBBBBB',
                        status: 'BBBBBB',
                        purpose: 'BBBBBB',
                        direction: 'BBBBBB',
                        fluid: 'BBBBBB',
                        dateTimeSpud: currentDate.format(DATE_TIME_FORMAT),
                        dateTimePA: currentDate.format(DATE_TIME_FORMAT),
                        headElevation: 1,
                        groundElevation: 1,
                        waterDepth: 1,
                        latitude: 'BBBBBB',
                        longitude: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        licenseDate: currentDate,
                        dateTimeSpud: currentDate,
                        dateTimePA: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Well', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});

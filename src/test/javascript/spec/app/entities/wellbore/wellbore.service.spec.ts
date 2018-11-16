/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WellboreService } from 'app/entities/wellbore/wellbore.service';
import { IWellbore, Wellbore, Status, BooleanType } from 'app/shared/model/wellbore.model';

describe('Service Tests', () => {
    describe('Wellbore Service', () => {
        let injector: TestBed;
        let service: WellboreService;
        let httpMock: HttpTestingController;
        let elemDefault: IWellbore;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(WellboreService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Wellbore(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                Status.Active,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                currentDate,
                BooleanType.Yes,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        kickOffDate: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Wellbore', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        kickOffDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        kickOffDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Wellbore(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Wellbore', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        wellName: 'BBBBBB',
                        parentWellboreName: 'BBBBBB',
                        govermentNumber: 'BBBBBB',
                        status: 'BBBBBB',
                        purpose: 'BBBBBB',
                        type: 'BBBBBB',
                        shape: 'BBBBBB',
                        dayTarget: 1,
                        kickOffDate: currentDate.format(DATE_TIME_FORMAT),
                        achievedTD: 'BBBBBB',
                        mdCurrent: 1,
                        tvdCurrent: 1,
                        mdBitCurrent: 1,
                        tvdBitCurrent: 1,
                        mdKickOff: 1,
                        tvdKickOff: 1,
                        mdPlanned: 1,
                        tvdPlanned: 1,
                        mdSubSea: 1,
                        tvdSubSea: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        kickOffDate: currentDate
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

            it('should return a list of Wellbore', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        wellName: 'BBBBBB',
                        parentWellboreName: 'BBBBBB',
                        govermentNumber: 'BBBBBB',
                        status: 'BBBBBB',
                        purpose: 'BBBBBB',
                        type: 'BBBBBB',
                        shape: 'BBBBBB',
                        dayTarget: 1,
                        kickOffDate: currentDate.format(DATE_TIME_FORMAT),
                        achievedTD: 'BBBBBB',
                        mdCurrent: 1,
                        tvdCurrent: 1,
                        mdBitCurrent: 1,
                        tvdBitCurrent: 1,
                        mdKickOff: 1,
                        tvdKickOff: 1,
                        mdPlanned: 1,
                        tvdPlanned: 1,
                        mdSubSea: 1,
                        tvdSubSea: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        kickOffDate: currentDate
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

            it('should delete a Wellbore', async () => {
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

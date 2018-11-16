/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { WitsServiceComponent } from 'app/entities/wits-service/wits-service.component';
import { WitsServiceService } from 'app/entities/wits-service/wits-service.service';
import { WitsService } from 'app/shared/model/wits-service.model';

describe('Component Tests', () => {
    describe('WitsService Management Component', () => {
        let comp: WitsServiceComponent;
        let fixture: ComponentFixture<WitsServiceComponent>;
        let service: WitsServiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WitsServiceComponent],
                providers: []
            })
                .overrideTemplate(WitsServiceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WitsServiceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WitsServiceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new WitsService(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.witsServices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

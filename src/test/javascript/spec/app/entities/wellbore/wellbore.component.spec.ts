/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { WellboreComponent } from 'app/entities/wellbore/wellbore.component';
import { WellboreService } from 'app/entities/wellbore/wellbore.service';
import { Wellbore } from 'app/shared/model/wellbore.model';

describe('Component Tests', () => {
    describe('Wellbore Management Component', () => {
        let comp: WellboreComponent;
        let fixture: ComponentFixture<WellboreComponent>;
        let service: WellboreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WellboreComponent],
                providers: []
            })
                .overrideTemplate(WellboreComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WellboreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WellboreService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Wellbore(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.wellbores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

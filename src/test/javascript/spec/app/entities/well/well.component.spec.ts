/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { WellComponent } from 'app/entities/well/well.component';
import { WellService } from 'app/entities/well/well.service';
import { Well } from 'app/shared/model/well.model';

describe('Component Tests', () => {
    describe('Well Management Component', () => {
        let comp: WellComponent;
        let fixture: ComponentFixture<WellComponent>;
        let service: WellService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WellComponent],
                providers: []
            })
                .overrideTemplate(WellComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WellComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WellService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Well(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.wells[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
